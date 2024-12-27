package org.UrlShortener.service;

import org.UrlShortener.data.UrlEntity;
import org.UrlShortener.dtos.request.UrlRequest;
import org.UrlShortener.dtos.response.UrlResponse;
import org.UrlShortener.repository.UrlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlRepo urlRepo;

    @Override
    public UrlResponse createShortUrl(UrlRequest urlRequest) {
        if (urlRequest.getLongUrl() == null || urlRequest.getLongUrl().isEmpty()){
            throw new IllegalArgumentException("Url cannot be empty");
        }
       String shortUrl = generateShortUrl(urlRequest.getLongUrl());
        UrlEntity existingUrl = urlRepo.findByShortUrl(shortUrl);
        if (existingUrl != null){
            UrlResponse urlResponse = new UrlResponse();
            urlResponse.setLongUrl(existingUrl.getLongUrl());
            urlResponse.setShortUrl(existingUrl.getShortUrl());
            return urlResponse;
        }

        UrlEntity urlEntity = new UrlEntity();
       urlEntity.setLongUrl(urlRequest.getLongUrl());
       urlEntity.setShortUrl(shortUrl);
       urlRepo.save(urlEntity);

       UrlResponse urlResponse = new UrlResponse();
       urlResponse.setLongUrl(urlEntity.getLongUrl());
       urlResponse.setShortUrl(urlEntity.getShortUrl());
       return urlResponse;
    }

    @Override
    public UrlResponse getLongUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()){
            throw new IllegalArgumentException("Url cannot be empty");
        }
        UrlEntity urlEntity = urlRepo.findByShortUrl(shortUrl);
        if (urlEntity == null) {
            throw new IllegalArgumentException("Url does not exist");
        }
        UrlEntity url = urlRepo.findByShortUrl(shortUrl);
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setLongUrl(url.getLongUrl());
        urlResponse.setShortUrl(url.getShortUrl());
        return urlResponse;
    }

    String generateShortUrl(String longUrl) {
        if(longUrl == null || longUrl.isEmpty()){
            throw new IllegalArgumentException("Url cannot be empty");
        }
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(longUrl.getBytes(StandardCharsets.UTF_8));
            String shortUrl = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return shortUrl.substring(0, 8);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException("Error generating short url");
        }
    }
}
