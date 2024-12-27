package org.UrlShortener.dtos.response;

import lombok.Data;

@Data
public class UrlResponse {
    private String longUrl;
    private String shortUrl;
}
