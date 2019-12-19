package com.github.juandavidgc.scalableweb.rest.api.messages;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Data
public class PartRequestMessageV1 {

    @NotBlank(message = "Base64 is mandatory")
    @ApiParam(value = "Base64 encoded json")
    private String base64;

}
