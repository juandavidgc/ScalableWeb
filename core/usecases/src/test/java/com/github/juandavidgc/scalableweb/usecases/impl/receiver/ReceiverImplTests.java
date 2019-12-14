package com.github.juandavidgc.scalableweb.usecases.impl.receiver;

import com.github.juandavidgc.scalableweb.entities.exceptions.NoJsonException;
import com.github.juandavidgc.scalableweb.entities.exceptions.NoValidBase64EncodingException;
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
    public static final String THE_LEFT_JSON = "eyJwYXJ0IjoidGhlX2xlZnQifQ==";
    public static final String THE_RIGHT_JSON = "eyJwYXJ0IjoidGhlX3JpZ2h0In0=";
    public static final String BAD_JSON = "eyJwYXJ0Ijp0aGVfcmlnaHR9";
    public static final String BAD_ENCODING_BASE64 = "BAD_JSON";

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
    public void receiveLeftPart() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left(THE_ID, THE_LEFT_JSON);

        verify(storeManager, times(1)).storeLeftPart(THE_ID, "{\"part\":\"the_left\"}");
    }

    @Test
    public void receiveRightPart() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right(THE_ID, THE_RIGHT_JSON);

        verify(storeManager, times(1)).storeRightPart(THE_ID, "{\"part\":\"the_right\"}");
    }

    @Test(expected = NoValidBase64EncodingException.class)
    public void receiveLeftPartBadEncodingBase64() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left(THE_ID, BAD_ENCODING_BASE64);
    }

    @Test(expected = NoValidBase64EncodingException.class)
    public void receiveRightPartBadEncodingBase64() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right(THE_ID, BAD_ENCODING_BASE64);
    }

    @Test(expected = NoJsonException.class)
    public void receiveLeftPartBadJson() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left(THE_ID, BAD_JSON);
    }

    @Test(expected = NoJsonException.class)
    public void receiveRightPartBadJson() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right(THE_ID, BAD_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveLeftPartEmptyBase64() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left(THE_ID, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveRightPartEmptyBase64() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right(THE_ID, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveLeftPartEmptyId() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left("", THE_LEFT_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveRightPartEmptyId() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right("", THE_RIGHT_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveLeftPartNullBase64() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left(THE_ID, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveRightPartNullBase64() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right(THE_ID, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveLeftPartNullId() throws NoJsonException, NoValidBase64EncodingException {
        receiver.left(null, THE_LEFT_JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void receiveRightPartNullId() throws NoJsonException, NoValidBase64EncodingException {
        receiver.right(null, THE_RIGHT_JSON);
    }
}
