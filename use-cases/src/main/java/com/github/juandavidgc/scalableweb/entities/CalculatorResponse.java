package com.github.juandavidgc.scalableweb.entities;

import lombok.Data;

import java.util.List;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Data
public class CalculatorResponse {

    private CalculatorResponseStatus responseStatus;

    private List<Difference> differenceList;

    public CalculatorResponse(CalculatorResponseStatus responseStatus){
        this.responseStatus = responseStatus;
    }

}
