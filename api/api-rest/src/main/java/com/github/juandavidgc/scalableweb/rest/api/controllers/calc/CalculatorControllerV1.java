package com.github.juandavidgc.scalableweb.rest.api.controllers.calc;

import com.github.juandavidgc.scalableweb.rest.api.mappers.Mapper;
import com.github.juandavidgc.scalableweb.rest.api.messages.CalculatorResponseMessageV1;
import com.github.juandavidgc.scalableweb.entities.exceptions.NotEnoughPartsException;
import com.github.juandavidgc.scalableweb.entities.usecases.calc.Calculator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Controller
@RequestMapping(value = "/v1/diff", produces = APPLICATION_JSON_VALUE)
@SwaggerDefinition
public class CalculatorControllerV1 {

    @Autowired
    private Calculator calculator;

    @ApiOperation(value = "Compares two base64 json strings", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 417, message = "Not enough parts (right, left or both)"),
            @ApiResponse(code = 400, message = "Empty ID")
    })
    @RequestMapping(value = "/{ID}", method = GET)
    public ResponseEntity<CalculatorResponseMessageV1> calculate(@PathVariable("ID") String id){
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity(Mapper.map(calculator.calculate(id)), OK);
        } catch (NotEnoughPartsException e) {
            responseEntity = new ResponseEntity(EXPECTATION_FAILED);
        } catch (IllegalArgumentException e) {
            responseEntity = new ResponseEntity(NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

}
