package org.UrlShortener.service;

import org.UrlShortener.dtos.request.UrlRequest;
import org.UrlShortener.dtos.response.UrlResponse;

public interface UrlService {
    UrlResponse createShortUrl(UrlRequest urlRequest);
    UrlResponse getLongUrl(String shortUrl);
//    String generateShortUrl(String longUrl);
}
