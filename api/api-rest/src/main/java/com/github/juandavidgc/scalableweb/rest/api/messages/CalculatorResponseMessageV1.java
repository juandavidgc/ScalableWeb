package com.github.juandavidgc.scalableweb.rest.api.messages;

import lombok.Data;

import java.util.List;

/**
 * Created by Jgutierrez on 14/12/2019.
 */
@Data
public class CalculatorResponseMessageV1 {

    private CalculatorResponseStatusV1 status;

    private List<DifferenceV1> differences;

}
