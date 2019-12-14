package com.github.juandavidgc.scalableweb.entities.usecases.receiver;

import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.entities.exceptions.NoValidBase64EncodingException;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
public interface Receiver {

    void left(String id, String base64json) throws NoValidBase64EncodingException, NoJsonException;

    void right(String id, String base64json) throws NoValidBase64EncodingException, NoJsonException;

}
