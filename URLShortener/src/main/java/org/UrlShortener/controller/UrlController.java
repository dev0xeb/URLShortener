package org.UrlShortener.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.UrlShortener.dtos.request.UrlRequest;
import org.UrlShortener.dtos.response.UrlResponse;
import org.UrlShortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> createShortUrl(@RequestBody UrlRequest urlRequest) {
        try{
            UrlResponse response = urlService.createShortUrl(urlRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlResponse> getLongUrl(@PathVariable String shortUrl) {
        try{
            UrlResponse response = urlService.getLongUrl(shortUrl);
            if (response == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
