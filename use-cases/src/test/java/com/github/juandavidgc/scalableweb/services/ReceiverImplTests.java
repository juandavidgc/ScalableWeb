package com.github.juandavidgc.scalableweb.services;

import com.github.juandavidgc.scalableweb.state.StoreManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReceiverImplTests.Config.class})
public class ReceiverImplTests {

    @Autowired
    private ReceiverImpl receiver;

    @MockBean
    private StoreManager storeManager;

    @Configuration
    public static class Config {

        @Bean
        public ReceiverImpl getReceiverImpl() {
            return new ReceiverImpl();
        }

    }

    @Test
    public void receiveLeftPart(){
        receiver.left("the_id", "the_left_json");

        Mockito.verify(storeManager, Mockito.times(1)).storeLeftPart("the_id", "the_left_json");
    }

    @Test
    public void receiveRightPart(){
        receiver.right("the_id", "the_right_json");

        Mockito.verify(storeManager, Mockito.times(1)).storeRightPart("the_id", "the_right_json");
    }
}
