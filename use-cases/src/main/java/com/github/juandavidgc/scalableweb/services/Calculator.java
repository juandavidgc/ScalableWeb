package com.github.juandavidgc.scalableweb.services;

import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface Calculator {

    CalculatorResponse calculate(String id);

}