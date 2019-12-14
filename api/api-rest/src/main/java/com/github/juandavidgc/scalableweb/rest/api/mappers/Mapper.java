package com.github.juandavidgc.scalableweb.rest.api.mappers;

import com.github.juandavidgc.scalableweb.rest.api.messages.CalculatorResponseMessageV1;
import com.github.juandavidgc.scalableweb.rest.api.messages.DifferenceV1;
import com.github.juandavidgc.scalableweb.rest.api.messages.CalculatorResponseStatusV1;
import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.domain.Difference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jgutierrez on 14/12/2019.
 */
public class Mapper {

    public static CalculatorResponseMessageV1 map(CalculatorResponse calculatorResponse) {
        CalculatorResponseMessageV1 calculatorResponseMessageV1 = new CalculatorResponseMessageV1();
        calculatorResponseMessageV1.setStatus(CalculatorResponseStatusV1.valueOf(
                calculatorResponse.getResponseStatus().name()));
        if (calculatorResponse.getDifferenceList() != null) {
            List<DifferenceV1> differenceV1List = new ArrayList<>();
            calculatorResponse.getDifferenceList().stream().forEach(difference -> differenceV1List.add(map(difference)));
            calculatorResponseMessageV1.setDifferences(differenceV1List);
        }
        return calculatorResponseMessageV1;
    }

    public static DifferenceV1 map(Difference difference) {
        DifferenceV1 differenceV1 = new DifferenceV1();
        differenceV1.setLength(difference.getLength());
        differenceV1.setPosition(difference.getPosition());
        return differenceV1;
    }

}
