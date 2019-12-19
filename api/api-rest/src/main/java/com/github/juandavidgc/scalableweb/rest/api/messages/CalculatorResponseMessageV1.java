package com.github.juandavidgc.scalableweb.rest.api.messages;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * Created by Jgutierrez on 14/12/2019.
 */
@Data
public class CalculatorResponseMessageV1 {

    @ApiParam(value = "Status of the comparisson")
    private CalculatorResponseStatusV1 status;

    @ApiParam(value = "List of differences")
    private List<DifferenceV1> differences;

}
