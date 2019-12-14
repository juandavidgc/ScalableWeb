package com.github.juandavidgc.scalableweb.usecases.impl.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.entities.usecases.receiver.Receiver;
import com.github.juandavidgc.scalableweb.entities.state.StoreManager;
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
    public void left(String id, String json) throws NoJsonException {
        if(noJson(json)){
            throw new NoJsonException();
        }
        storeManager.storeLeftPart(id, json);
    }

    @Override
    public void right(String id, String json) throws NoJsonException {
        if(noJson(json)){
            throw new NoJsonException();
        }
        storeManager.storeRightPart(id, json);
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
