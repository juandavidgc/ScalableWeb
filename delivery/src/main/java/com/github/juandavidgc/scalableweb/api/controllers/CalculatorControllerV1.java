package com.github.juandavidgc.scalableweb.api.controllers;

import com.github.juandavidgc.scalableweb.business.Calculator;
import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;
import com.github.juandavidgc.scalableweb.exceptions.NotEnoughPartsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Controller
@RequestMapping(value = "/v1/diff", produces = APPLICATION_JSON_VALUE)
public class CalculatorControllerV1 {

    @Autowired
    private Calculator calculator;

    @RequestMapping(value = "/{ID}", method = GET)
    public ResponseEntity<CalculatorResponse> receiveLeft(@PathVariable("ID") String id){
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity(calculator.calculate(id), OK);
        } catch (NotEnoughPartsException e) {
            responseEntity = new ResponseEntity(EXPECTATION_FAILED);
        }
        return responseEntity;
    }

}
