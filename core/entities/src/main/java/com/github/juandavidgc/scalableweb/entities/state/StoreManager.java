package com.github.juandavidgc.scalableweb.entities.state;

import com.github.juandavidgc.scalableweb.entities.domain.Parts;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface StoreManager {

    void storeLeftPart(String id, String json);

    void storeRightPart(String id, String json);

    Parts retrievePartsById(String id);

}
