package com.github.juandavidgc.scalableweb.services;

import com.github.juandavidgc.scalableweb.state.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Service
public class ReceiverImpl implements Receiver {

    @Autowired
    private StoreManager storeManager;

    @Override
    public void left(String id, String json) {
        storeManager.storeLeftPart(id, json);
    }

    @Override
    public void right(String id, String json) {
        storeManager.storeRightPart(id, json);
    }
}
