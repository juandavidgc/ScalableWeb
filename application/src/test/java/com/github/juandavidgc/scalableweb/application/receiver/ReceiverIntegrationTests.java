package com.github.juandavidgc.scalableweb.application.receiver;

import com.github.juandavidgc.scalableweb.rest.api.messages.PartRequestMessageV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReceiverIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private <T> HttpEntity<T> createEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        List<MediaType> types = new ArrayList<>(0);
        types.add(APPLICATION_JSON);
        headers.setAccept(types);
        return new HttpEntity<>(request, headers);
    }

    @Test
    public void receiveLeft(){
        PartRequestMessageV1 partRequestV1 = new PartRequestMessageV1();
        String json = "{\"one\":1,\"two\":2}";
        partRequestV1.setBase64(new String(Base64.encodeBase64(json.getBytes())));
        HttpEntity<PartRequestMessageV1> entity = createEntity(partRequestV1);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/diff/1/left"),
                PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void receiveLeftBadJson(){
        PartRequestMessageV1 partRequestV1 = new PartRequestMessageV1();
        String json = "{\"one\":1,\"\"two\":2}";
        partRequestV1.setBase64(new String(Base64.encodeBase64(json.getBytes())));
        HttpEntity<PartRequestMessageV1> entity = createEntity(partRequestV1);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/diff/1/left"),
                PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_ACCEPTABLE);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void receiveRight(){
        PartRequestMessageV1 partRequestV1 = new PartRequestMessageV1();
        String json = "{\"one\":1,\"two\":2}";
        partRequestV1.setBase64(new String(Base64.encodeBase64(json.getBytes())));
        HttpEntity<PartRequestMessageV1> entity = createEntity(partRequestV1);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/diff/1/right"),
                PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void receiveRightBadJson(){
        PartRequestMessageV1 partRequestV1 = new PartRequestMessageV1();
        String json = "{\"one\":1,\"two\":juancho}";
        partRequestV1.setBase64(new String(Base64.encodeBase64(json.getBytes())));
        HttpEntity<PartRequestMessageV1> entity = createEntity(partRequestV1);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/diff/1/right"),
                PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_ACCEPTABLE);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void receiveRightDataEmpty(){
        PartRequestMessageV1 partRequestV1 = new PartRequestMessageV1();
        String json = null;
        partRequestV1.setBase64(json);
        HttpEntity<PartRequestMessageV1> entity = createEntity(partRequestV1);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/diff/1/right"),
                PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void receiveLeftDataEmpty(){
        PartRequestMessageV1 partRequestV1 = new PartRequestMessageV1();
        String json = null;
        partRequestV1.setBase64(json);
        HttpEntity<PartRequestMessageV1> entity = createEntity(partRequestV1);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/diff/1/left"),
                PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

}
