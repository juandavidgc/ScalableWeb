package com.github.juandavidgc.scalableweb.usecases.impl.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.entities.exceptions.NoValidBase64EncodingException;
import com.github.juandavidgc.scalableweb.entities.usecases.receiver.Receiver;
import com.github.juandavidgc.scalableweb.entities.state.StoreManager;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Component
public class ReceiverImpl implements Receiver {

    @Autowired
    private StoreManager storeManager;

    @Override
    public void left(String id, String base64json) throws NoValidBase64EncodingException, NoJsonException {
        if(isEmpty(id) || isEmpty(base64json)){
            throw new IllegalArgumentException();
        }
        String json = getStringFromBase64(base64json);
        if(noJson(json)){
            throw new NoJsonException();
        }
        storeManager.storeLeftPart(id, json);
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    @Override
    public void right(String id, String base64json) throws NoValidBase64EncodingException, NoJsonException {
        if(isEmpty(id) || isEmpty(base64json)){
            throw new IllegalArgumentException();
        }
        String json = getStringFromBase64(base64json);
        if(noJson(json)){
            throw new NoJsonException();
        }
        storeManager.storeRightPart(id, json);
    }

    private String getStringFromBase64(String base64json) throws NoValidBase64EncodingException {
        try {
            return new String(Base64.decodeBase64(base64json.getBytes()));
        } catch (IllegalArgumentException e){
            throw new NoValidBase64EncodingException();
        }
    }

    private boolean noJson(String json) {
        boolean noJson = false;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
        } catch (IOException e) {
            noJson = true;
        }
        return noJson;
    }
}
