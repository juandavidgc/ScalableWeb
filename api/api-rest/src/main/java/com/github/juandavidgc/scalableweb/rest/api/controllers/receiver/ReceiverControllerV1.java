package com.github.juandavidgc.scalableweb.rest.api.controllers.receiver;

import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.rest.api.messages.PartRequestMessageV1;
import com.github.juandavidgc.scalableweb.entities.usecases.receiver.Receiver;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
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
    public ResponseEntity receiveLeft(@RequestBody @Valid PartRequestMessageV1 partRequest,
                                      @PathVariable("ID") String id){
        ResponseEntity responseEntity = new ResponseEntity(OK);
        try {
            receiver.left(id, new String(Base64.decodeBase64(partRequest.getBase64().getBytes())));
        } catch (NoJsonException e) {
            responseEntity = new ResponseEntity(NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/{ID}/right", method = PUT)
    public ResponseEntity receiveRight(@RequestBody @Valid PartRequestMessageV1 partRequest,
                                       @PathVariable("ID") String id){
        ResponseEntity responseEntity = new ResponseEntity(OK);
        try {
            receiver.right(id, new String(Base64.decodeBase64(partRequest.getBase64().getBytes())));
        } catch (NoJsonException e) {
            responseEntity = new ResponseEntity(NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

}
