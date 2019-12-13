package com.github.juandavidgc.scalableweb.services;

import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus;
import com.github.juandavidgc.scalableweb.entities.Parts;
import com.github.juandavidgc.scalableweb.exceptions.NotEnoughPartsException;
import com.github.juandavidgc.scalableweb.state.StoreManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.EQUAL;
import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.NOT_EQUAL;
import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.SAME_SIZE_BUT_DIFFERENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

    @Test
    public void equalParts() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"part\":\"equal\"}");
        parts.setLeft("{\"part\":\"equal\"}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(EQUAL, calculatorResponse.getResponseStatus());
        assertNull(calculatorResponse.getDifferenceList());
    }

    @Test
    public void differentSize() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"part\":\"size_small\"}");
        parts.setLeft("{\"part\":\"size_different\"}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(NOT_EQUAL, calculatorResponse.getResponseStatus());
        assertNull(calculatorResponse.getDifferenceList());
    }

    @Test
    public void sameSizeWithOneDifference() throws NotEnoughPartsException {
        Parts parts = new Parts();
        parts.setRight("{\"one\":1,\"two\":2}");
        parts.setLeft("{\"oee\":1,\"two\":2}");
        when(storeManager.retrievePartsById("the_id")).thenReturn(parts);

        CalculatorResponse calculatorResponse = calculator.calculate("the_id");

        assertEquals(SAME_SIZE_BUT_DIFFERENT, calculatorResponse.getResponseStatus());
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

        assertEquals(SAME_SIZE_BUT_DIFFERENT, calculatorResponse.getResponseStatus());
        assertNotNull(calculatorResponse.getDifferenceList());
        assertEquals(2, calculatorResponse.getDifferenceList().size());
        assertEquals(3, calculatorResponse.getDifferenceList().get(0).getPosition());
        assertEquals(1, calculatorResponse.getDifferenceList().get(0).getLength());
        assertEquals(11, calculatorResponse.getDifferenceList().get(1).getPosition());
        assertEquals(2, calculatorResponse.getDifferenceList().get(1).getLength());
    }
}
