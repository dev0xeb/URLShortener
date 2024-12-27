package org.UrlShortener.service;

import org.UrlShortener.data.UrlEntity;
import org.UrlShortener.dtos.request.UrlRequest;
import org.UrlShortener.dtos.response.UrlResponse;
import org.UrlShortener.repository.UrlRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UrlServiceImplTest {
    @Autowired
    private UrlServiceImpl urlService;
    @Autowired
    private UrlRepo urlRepo;

    @BeforeEach
    public void setUp() {
        urlRepo.deleteAll();
    }

    @Test
    public void testCreateShortUrl() {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setLongUrl("https://hireclinton.com");

        UrlResponse urlResponse = urlService.createShortUrl(urlRequest);
        assertNotNull(urlResponse);
        assertNotNull(urlResponse.getShortUrl());
        assertEquals("https://hireclinton.com", urlResponse.getLongUrl());
    }

    @Test
    public void testCreateShortUrlWithEmptyLongUrl(){
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setLongUrl("");

        Exception invalidUrlException = assertThrows(IllegalArgumentException.class, () -> {
            urlService.createShortUrl(urlRequest);
        });
        String expectedMessage = "Url cannot be empty";
        String actualMessage = invalidUrlException.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void testCreateShortUrlWithForeignCharacters() {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setLongUrl("https://hireclinton.com/こんにちは");

        UrlResponse urlResponse = urlService.createShortUrl(urlRequest);
        assertNotNull(urlResponse);
        assertNotNull(urlResponse.getShortUrl());
        assertEquals("https://hireclinton.com/こんにちは", urlResponse.getLongUrl());
    }

    @Test
    public void testGetLongUrl(){
        UrlEntity url = new UrlEntity();
        url.setLongUrl("https://hireclinton.com");
        url.setShortUrl("shortUrl");
        urlRepo.save(url);

        UrlResponse urlResponse = urlService.getLongUrl("shortUrl");
        assertNotNull(urlResponse);
        assertEquals("shortUrl", urlResponse.getShortUrl());
        assertEquals("https://hireclinton.com", url.getLongUrl());
    }

    @Test
    public void testGetLongUrlNotFound(){
//        UrlResponse urlResponse = urlService.getLongUrl("invalid url");
        Exception notFoundException = assertThrows(IllegalArgumentException.class, () -> {
            urlService.getLongUrl("invalid url");
        });
        String expectedMessage = "Url does not exist";
        String actualMessage = notFoundException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetShortUrl(){
        Exception notFoundException = assertThrows(IllegalArgumentException.class, () -> {
            urlService.getLongUrl("");
        });
        String expectedMessage = "Url cannot be empty";
        String actualMessage = notFoundException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGenerateShortUrl(){
        String longUrl = "https://hireclinton.com";
        String shortUrl = urlService.generateShortUrl(longUrl);

        assertNotNull(shortUrl);
        assertEquals(8, shortUrl.length());
    }

    @Test
    public void testGenerateShortUrlWithSameLongUrl(){
        String longUrl = "https://hireclinton.com";
        String shortUrl = urlService.generateShortUrl(longUrl);
        String shortUrl2 = urlService.generateShortUrl(longUrl);

        assertEquals(shortUrl, shortUrl2);
    }

    @Test
    public void testGenerateShortUrlWithDifferentLongUrl(){
        String longUrl = "https://hireclinton.com";
        String longUrl2 = "https://hiredev0xbig-c.com";
        String shortUrl = urlService.generateShortUrl(longUrl);
        String shortUrl2 = urlService.generateShortUrl(longUrl2);

        assertNotEquals(shortUrl, shortUrl2);
    }

    @Test
    public void testGenerateShortUrlWithEmptyLongUrl(){
        String longUrl = "";
        Exception emptyLongUrlException = assertThrows(IllegalArgumentException.class, () -> {
            urlService.generateShortUrl(longUrl);
        });
        String expectedMessage = "Url cannot be empty";
        String actualMessage = emptyLongUrlException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGenerateShortUrlWithForeignCharacters(){
        String longUrl = "https://hireclinton.com/こんにちは";
        String shortUrl = urlService.generateShortUrl(longUrl);
        assertNotNull(shortUrl);
        assertEquals(8, shortUrl.length());
    }

}