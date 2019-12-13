package com.github.juandavidgc.scalableweb.business;

import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;
import com.github.juandavidgc.scalableweb.exceptions.NotEnoughPartsException;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface Calculator {

    CalculatorResponse calculate(String id) throws NotEnoughPartsException;

}
