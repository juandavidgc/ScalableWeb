package com.github.juandavidgc.scalableweb.api.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Data
public class PartRequestV1 {

    @NotBlank(message = "Base64 is mandatory")
    private String base64;

}
