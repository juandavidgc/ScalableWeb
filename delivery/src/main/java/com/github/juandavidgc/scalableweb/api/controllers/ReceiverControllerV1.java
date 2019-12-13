package com.github.juandavidgc.scalableweb.api.controllers;

import com.github.juandavidgc.scalableweb.api.entities.PartRequestV1;
import com.github.juandavidgc.scalableweb.business.Receiver;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Controller
@RequestMapping(value = "/v1/diff", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class ReceiverControllerV1 {

    @Autowired
    private Receiver receiver;

    @RequestMapping(value = "/{ID}/left", method = PUT)
    public ResponseEntity receiveLeft(@RequestBody @Valid PartRequestV1 partRequest, @PathVariable("ID") String id){
        receiver.left(id, new String(Base64.decodeBase64(partRequest.getBase64().getBytes())));
        return new ResponseEntity(OK);
    }

    @RequestMapping(value = "/{ID}/right", method = PUT)
    public ResponseEntity receiveRight(@RequestBody @Valid PartRequestV1 partRequest, @PathVariable("ID") String id){
        receiver.right(id, new String(Base64.decodeBase64(partRequest.getBase64().getBytes())));
        return new ResponseEntity(OK);
    }

}
