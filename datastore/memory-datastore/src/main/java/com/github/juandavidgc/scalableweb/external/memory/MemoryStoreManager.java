package com.github.juandavidgc.scalableweb.external.memory;

import com.github.juandavidgc.scalableweb.entities.domain.Parts;
import com.github.juandavidgc.scalableweb.entities.state.StoreManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Component
public class MemoryStoreManager implements StoreManager {

    private Map<String, Parts> stateInMemory;

    public MemoryStoreManager() {
        this.stateInMemory = new HashMap<>(); //TODO Evaluate if hashmap is the better option
    }

    @Override
    public void storeLeftPart(String id, String json) {
        Parts parts = new Parts();
        if(stateInMemory.containsKey(id)){
            parts = stateInMemory.get(id);
        }
        parts.setLeft(json);
        stateInMemory.put(id, parts);
    }

    @Override
    public void storeRightPart(String id, String json) {
        Parts parts = new Parts();
        if(stateInMemory.containsKey(id)){
            parts = stateInMemory.get(id);
        }
        parts.setRight(json);
        stateInMemory.put(id, parts);
    }

    @Override
    public Parts retrievePartsById(String id) {
        return stateInMemory.get(id);
    }
}
