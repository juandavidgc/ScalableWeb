package com.github.juandavidgc.scalableweb.entities.usecases.receiver;

import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface Receiver {

    void left(String id, String json) throws NoJsonException;

    void right(String id, String json) throws NoJsonException;

}
