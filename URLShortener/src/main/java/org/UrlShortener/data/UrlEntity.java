package org.UrlShortener.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "urls")
@Data
public class UrlEntity {
    @Id
    private String id;
    private String longUrl;
    private String shortUrl;
}
