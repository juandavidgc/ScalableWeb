package com.github.juandavidgc.scalableweb.usecases.impl.receiver;

import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.entities.state.StoreManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReceiverImplTests.Config.class})
public class ReceiverImplTests {

    public static final String THE_ID = "the_id";
    public static final String THE_LEFT_JSON = "{\"part\":\"the_left\"}";
    public static final String THE_RIGHT_JSON = "{\"part\":\"the_right\"}";
    public static final String BAD_JSON = "BAD_JSON";

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
    public void receiveLeftPart() throws NoJsonException {
        receiver.left(THE_ID, THE_LEFT_JSON);

        verify(storeManager, times(1)).storeLeftPart(THE_ID, THE_LEFT_JSON);
    }

    @Test
    public void receiveRightPart() throws NoJsonException {
        receiver.right(THE_ID, THE_RIGHT_JSON);

        verify(storeManager, times(1)).storeRightPart(THE_ID, THE_RIGHT_JSON);
    }

    @Test(expected = NoJsonException.class)
    public void receiveLeftPartBadJson() throws NoJsonException {
        receiver.left(THE_ID, BAD_JSON);
    }

    @Test(expected = NoJsonException.class)
    public void receiveRightPartBadJson() throws NoJsonException {
        receiver.right(THE_ID, BAD_JSON);
    }
}
