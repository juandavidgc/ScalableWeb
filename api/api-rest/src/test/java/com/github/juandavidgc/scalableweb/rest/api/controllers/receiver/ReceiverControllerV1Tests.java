package com.github.juandavidgc.scalableweb.rest.api.controllers.receiver;

import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.entities.exceptions.NoValidBase64EncodingException;
import com.github.juandavidgc.scalableweb.entities.usecases.receiver.Receiver;
import com.github.juandavidgc.scalableweb.rest.api.messages.PartRequestMessageV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Jgutierrez on 14/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReceiverControllerV1Tests.Config.class})
public class ReceiverControllerV1Tests {

    public static final String THE_ID = "the_id";
    public static final String THE_BASE_64 = "THE_BASE64";

    @Autowired
    private ReceiverControllerV1 receiverControllerV1;

    @MockBean
    private Receiver receiver;

    @Configuration
    public static class Config {

        @Bean
        public ReceiverControllerV1 getReceiverControllerV1() {
            return new ReceiverControllerV1();
        }

    }

    @Test
    public void receiveLeftPartOk(){
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        ResponseEntity responseEntity = receiverControllerV1.receiveLeft(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void receiveLeftPartNoJsonException() throws NoJsonException, NoValidBase64EncodingException {
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        Mockito.doThrow(new NoJsonException()).when(receiver).left(THE_ID, THE_BASE_64);

        ResponseEntity responseEntity = receiverControllerV1.receiveLeft(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    public void receiveLeftPartNoValidBase64EncodingException() throws NoJsonException, NoValidBase64EncodingException {
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        Mockito.doThrow(new NoValidBase64EncodingException()).when(receiver).left(THE_ID, THE_BASE_64);

        ResponseEntity responseEntity = receiverControllerV1.receiveLeft(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    public void receiveLeftPartIllegalArgumentException() throws NoJsonException, NoValidBase64EncodingException {
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        Mockito.doThrow(new IllegalArgumentException()).when(receiver).left(THE_ID, THE_BASE_64);

        ResponseEntity responseEntity = receiverControllerV1.receiveLeft(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    public void receiveRightPartOk(){
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        ResponseEntity responseEntity = receiverControllerV1.receiveRight(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void receiveRightPartNoJsonException() throws NoJsonException, NoValidBase64EncodingException {
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        Mockito.doThrow(new NoJsonException()).when(receiver).right(THE_ID, THE_BASE_64);

        ResponseEntity responseEntity = receiverControllerV1.receiveRight(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    public void receiveRightPartNoValidBase64EncodingException() throws NoJsonException, NoValidBase64EncodingException {
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        Mockito.doThrow(new NoValidBase64EncodingException()).when(receiver).right(THE_ID, THE_BASE_64);

        ResponseEntity responseEntity = receiverControllerV1.receiveRight(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    public void receiveRightPartIllegalArgumentException() throws NoJsonException, NoValidBase64EncodingException {
        PartRequestMessageV1 partRequestMessageV1 = new PartRequestMessageV1();
        partRequestMessageV1.setBase64(THE_BASE_64);
        Mockito.doThrow(new IllegalArgumentException()).when(receiver).right(THE_ID, THE_BASE_64);

        ResponseEntity responseEntity = receiverControllerV1.receiveRight(partRequestMessageV1, THE_ID);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }
}
