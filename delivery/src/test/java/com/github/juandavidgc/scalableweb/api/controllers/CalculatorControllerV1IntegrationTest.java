package com.github.juandavidgc.scalableweb.api.controllers;

import com.github.juandavidgc.scalableweb.api.entities.PartRequestV1;
import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.EQUAL;
import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.NOT_EQUAL;
import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.SAME_SIZE_BUT_DIFFERENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerV1IntegrationTest {

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

    private void sendRightData(String json, String id) {
        PartRequestV1 partRequestV1 = new PartRequestV1();
        partRequestV1.setBase64(new String(Base64.encodeBase64(json.getBytes())));
        HttpEntity<PartRequestV1> entity = createEntity(partRequestV1);

        restTemplate.exchange(createURLWithPort("/v1/diff/" + id + "/right"),
                PUT, entity, String.class);
    }

    private void sendLeftData(String json, String id) {
        PartRequestV1 partRequestV1 = new PartRequestV1();
        partRequestV1.setBase64(new String(Base64.encodeBase64(json.getBytes())));
        HttpEntity<PartRequestV1> entity = createEntity(partRequestV1);

        restTemplate.exchange(createURLWithPort("/v1/diff/" + id + "/left"),
                PUT, entity, String.class);
    }

    @Test
    public void calculateWithNoData() {
        ResponseEntity<CalculatorResponse> response = restTemplate.exchange(createURLWithPort("/v1/diff/1"),
                HttpMethod.GET, null, CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(EXPECTATION_FAILED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void calculateWithNoLeftData() {
        sendRightData("{\"one\":1,\"two\":2}", "1");

        ResponseEntity<CalculatorResponse> response = restTemplate.exchange(createURLWithPort("/v1/diff/1"),
                HttpMethod.GET, null, CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(EXPECTATION_FAILED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void calculateWithNoRightData() {
        sendLeftData("{\"one\":1,\"two\":2}", "2");

        ResponseEntity<CalculatorResponse> response = restTemplate.exchange(createURLWithPort("/v1/diff/2"),
                HttpMethod.GET, null, CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(EXPECTATION_FAILED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void calculateEquals() {
        sendLeftData("{\"one\":1,\"two\":2}", "3");
        sendRightData("{\"one\":1,\"two\":2}", "3");

        ResponseEntity<CalculatorResponse> response = restTemplate.getForEntity(createURLWithPort("/v1/diff/3"),
                CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getResponseStatus()).isEqualTo(EQUAL);
    }

    @Test
    public void calculateNotEquals() {
        sendLeftData("{\"one\":1,\"two\":2}", "4");
        sendRightData("{\"one\":1,\"two\":2,\"three\":3}", "4");

        ResponseEntity<CalculatorResponse> response = restTemplate.exchange(createURLWithPort("/v1/diff/4"),
                HttpMethod.GET, null, CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getResponseStatus()).isEqualTo(NOT_EQUAL);
    }

    @Test
    public void calculateSameSizeOneDifference() {
        sendLeftData("{\"one\":1,\"two\":2}", "5");
        sendRightData("{\"oye\":1,\"two\":2}", "5");

        ResponseEntity<CalculatorResponse> response = restTemplate.exchange(createURLWithPort("/v1/diff/5"),
                HttpMethod.GET, null, CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getResponseStatus()).isEqualTo(SAME_SIZE_BUT_DIFFERENT);
        assertThat(response.getBody().getDifferenceList().size()).isEqualTo(1);
        assertThat(response.getBody().getDifferenceList().get(0).getPosition()).isEqualTo(3);
        assertThat(response.getBody().getDifferenceList().get(0).getLength()).isEqualTo(1);
    }

    @Test
    public void calculateSameSizeTwoDifferences() {
        sendLeftData("{\"one\":1,\"two\":2}", "6");
        sendRightData("{\"oye\":1,\"aaa\":2}", "6");

        ResponseEntity<CalculatorResponse> response = restTemplate.exchange(createURLWithPort("/v1/diff/6"),
                HttpMethod.GET, null, CalculatorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getResponseStatus()).isEqualTo(SAME_SIZE_BUT_DIFFERENT);
        assertThat(response.getBody().getDifferenceList().size()).isEqualTo(2);
        assertThat(response.getBody().getDifferenceList().get(0).getPosition()).isEqualTo(3);
        assertThat(response.getBody().getDifferenceList().get(0).getLength()).isEqualTo(1);
        assertThat(response.getBody().getDifferenceList().get(1).getPosition()).isEqualTo(10);
        assertThat(response.getBody().getDifferenceList().get(1).getLength()).isEqualTo(3);
    }

}
