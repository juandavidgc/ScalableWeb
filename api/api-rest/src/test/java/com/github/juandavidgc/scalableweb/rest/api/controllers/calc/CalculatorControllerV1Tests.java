package com.github.juandavidgc.scalableweb.rest.api.controllers.calc;

import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponseStatus;
import com.github.juandavidgc.scalableweb.entities.domain.Difference;
import com.github.juandavidgc.scalableweb.entities.exceptions.NotEnoughPartsException;
import com.github.juandavidgc.scalableweb.entities.usecases.calc.Calculator;
import com.github.juandavidgc.scalableweb.rest.api.messages.CalculatorResponseMessageV1;
import com.github.juandavidgc.scalableweb.rest.api.messages.PartRequestMessageV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jgutierrez on 14/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalculatorControllerV1Tests.Config.class})
public class CalculatorControllerV1Tests {

    @Autowired
    private CalculatorControllerV1 calculatorControllerV1;

    @MockBean
    private Calculator calculator;

    @Configuration
    public static class Config {

        @Bean
        public CalculatorControllerV1 getCalculatorControllerV1() {
            return new CalculatorControllerV1();
        }

    }

    @Test
    public void calculateEqual() throws NotEnoughPartsException {
        CalculatorResponse calculatorResponse = new CalculatorResponse(CalculatorResponseStatus.EQUAL);
        Mockito.when(calculator.calculate("the_id")).thenReturn(calculatorResponse);

        ResponseEntity<CalculatorResponseMessageV1> responseEntity = calculatorControllerV1.calculate("the_id");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("EQUAL", responseEntity.getBody().getStatus().name());
        assertNull(responseEntity.getBody().getDifferences());
    }

    @Test
    public void calculateNotEqual() throws NotEnoughPartsException {
        CalculatorResponse calculatorResponse = new CalculatorResponse(CalculatorResponseStatus.NOT_EQUAL);
        Mockito.when(calculator.calculate("the_id")).thenReturn(calculatorResponse);

        ResponseEntity<CalculatorResponseMessageV1> responseEntity = calculatorControllerV1.calculate("the_id");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("NOT_EQUAL", responseEntity.getBody().getStatus().name());
        assertNull(responseEntity.getBody().getDifferences());
    }

    @Test
    public void calculateSameSizeButDifferent() throws NotEnoughPartsException {
        CalculatorResponse calculatorResponse = new CalculatorResponse(CalculatorResponseStatus.SAME_SIZE_BUT_DIFFERENT);
        calculatorResponse.setDifferenceList(Arrays.asList(new Difference[]{new Difference(2, 3), new Difference(5, 1)}));
        Mockito.when(calculator.calculate("the_id")).thenReturn(calculatorResponse);

        ResponseEntity<CalculatorResponseMessageV1> responseEntity = calculatorControllerV1.calculate("the_id");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SAME_SIZE_BUT_DIFFERENT", responseEntity.getBody().getStatus().name());
        assertEquals(2, responseEntity.getBody().getDifferences().size());
        assertEquals(2, responseEntity.getBody().getDifferences().get(0).getPosition());
        assertEquals(3, responseEntity.getBody().getDifferences().get(0).getLength());
        assertEquals(5, responseEntity.getBody().getDifferences().get(1).getPosition());
        assertEquals(1, responseEntity.getBody().getDifferences().get(1).getLength());
    }

}
