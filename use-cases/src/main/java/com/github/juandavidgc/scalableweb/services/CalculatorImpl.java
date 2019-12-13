package com.github.juandavidgc.scalableweb.services;

import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;
import com.github.juandavidgc.scalableweb.state.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Service
public class CalculatorImpl implements Calculator {

    @Autowired
    private StoreManager storeManager;

    @Override
    public CalculatorResponse calculate(String id) {
        return null;
    }
}
