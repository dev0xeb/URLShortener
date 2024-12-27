package org.UrlShortener.controller;

import org.UrlShortener.dtos.request.UrlRequest;
import org.UrlShortener.dtos.response.UrlResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAndGetShortUrl(){
        UrlRequest request = new UrlRequest();
        request.setLongUrl("https://hireclinton.com");

        ResponseEntity<UrlResponse> createResponse = restTemplate.postForEntity("/api/shorten", request, UrlResponse.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        String shortUrl = createResponse.getBody().getShortUrl();

        ResponseEntity<UrlResponse> getResponse = restTemplate.exchange("/api/" + shortUrl, HttpMethod.GET, HttpEntity.EMPTY, UrlResponse.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("https://hireclinton.com", getResponse.getBody().getLongUrl());
    }

}