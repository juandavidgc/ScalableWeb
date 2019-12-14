package com.github.juandavidgc.scalableweb.entities.usecases;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface Receiver {

    void left(String id, String json);

    void right(String id, String json);

}
