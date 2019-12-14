package com.github.juandavidgc.scalableweb.rest.api.controllers.receiver;

import com.github.juandavidgc.scalableweb.entities.usecases.receiver.Receiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Jgutierrez on 14/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReceiverControllerV1Tests.Config.class})
public class ReceiverControllerV1Tests {

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
    public void ok(){

    }

}
