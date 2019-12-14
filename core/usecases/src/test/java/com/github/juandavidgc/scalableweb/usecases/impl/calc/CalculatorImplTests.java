package com.github.juandavidgc.scalableweb.usecases.impl.calc;

import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.domain.Parts;
import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponseStatus;
import com.github.juandavidgc.scalableweb.entities.exceptions.NotEnoughPartsException;
import com.github.juandavidgc.scalableweb.entities.state.StoreManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalculatorImplTests.Config.class})
public class CalculatorImplTests {

    @Autowired
    private CalculatorImpl calculator;

    @MockBean
    private StoreManager storeManager;

    @Configuration
    public static class Config {

        @Bean
        public CalculatorImpl getCalculatorImpl() {
            return new CalculatorImpl();
        }

    }

    @Test(expected = NotEnoughPartsException.class)
    public void noLeftPart() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"part\":\"the_right\"}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        calculator.calculate("the_id");
    }

    @Test(expected = NotEnoughPartsException.class)
    public void noRightPart() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setLeft("{\"part\":\"the_left\"}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        calculator.calculate("the_id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullId() throws NotEnoughPartsException {
        calculator.calculate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyId() throws NotEnoughPartsException {
        calculator.calculate("");
    }

    @Test
    public void equalParts() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"part\":\"equal\"}");
        parts.setLeft("{\"part\":\"equal\"}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(CalculatorResponseStatus.EQUAL, calculatorResponse.getResponseStatus());
        assertNull(calculatorResponse.getDifferenceList());
    }

    @Test
    public void differentSize() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"part\":\"size_small\"}");
        parts.setLeft("{\"part\":\"size_different\"}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(CalculatorResponseStatus.NOT_EQUAL, calculatorResponse.getResponseStatus());
        assertNull(calculatorResponse.getDifferenceList());
    }

    @Test
    public void sameSizeWithOneDifference() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"one\":1,\"two\":2}");
        parts.setLeft("{\"oee\":1,\"two\":2}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(CalculatorResponseStatus.SAME_SIZE_BUT_DIFFERENT, calculatorResponse.getResponseStatus());
        assertNotNull(calculatorResponse.getDifferenceList());
        assertEquals(1, calculatorResponse.getDifferenceList().size());
        assertEquals(3, calculatorResponse.getDifferenceList().get(0).getPosition());
        assertEquals(1, calculatorResponse.getDifferenceList().get(0).getLength());
    }

    @Test
    public void sameSizeWithTwoDifferences() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"one\":1,\"two\":2}");
        parts.setLeft("{\"oee\":1,\"tuu\":2}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(CalculatorResponseStatus.SAME_SIZE_BUT_DIFFERENT, calculatorResponse.getResponseStatus());
        assertNotNull(calculatorResponse.getDifferenceList());
        assertEquals(2, calculatorResponse.getDifferenceList().size());
        assertEquals(3, calculatorResponse.getDifferenceList().get(0).getPosition());
        assertEquals(1, calculatorResponse.getDifferenceList().get(0).getLength());
        assertEquals(11, calculatorResponse.getDifferenceList().get(1).getPosition());
        assertEquals(2, calculatorResponse.getDifferenceList().get(1).getLength());
    }
}
