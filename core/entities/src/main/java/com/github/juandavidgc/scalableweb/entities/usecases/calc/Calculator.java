package com.github.juandavidgc.scalableweb.entities.usecases.calc;

import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.exceptions.NotEnoughPartsException;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface Calculator {

    CalculatorResponse calculate(String id) throws NotEnoughPartsException;

}
