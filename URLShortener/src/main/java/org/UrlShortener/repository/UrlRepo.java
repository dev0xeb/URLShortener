package org.UrlShortener.repository;

import org.UrlShortener.data.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepo extends MongoRepository<UrlEntity, String> {
    UrlEntity findByShortUrl(String shortUrl);
}
