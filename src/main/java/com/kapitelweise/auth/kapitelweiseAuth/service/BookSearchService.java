package com.kapitelweise.auth.kapitelweiseAuth.service;

import com.kapitelweise.auth.kapitelweiseAuth.request.BookSearchRequest;
import com.kapitelweise.auth.kapitelweiseAuth.response.BookSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;


@Service
public class BookSearchService {


    private final RestClient restClient = RestClient.create();
    private final String lang = "ger";

private static final Logger log = LoggerFactory.getLogger(BookSearchService.class);

    public BookSearchResponse searchBooks(BookSearchRequest request) {
        return restClient.get()
                .uri(uriBuilder -> {
                    var uri = uriBuilder
                            .scheme("https")
                            .host("openlibrary.org")
                            .path("/search.json")
                            .queryParam("limit", 10)
                            .queryParam("language", lang);

                    addIfPresent(uri,"q", request.getQ());
                    addIfPresent(uri, "title", request.getTitle());
                    addIfPresent(uri, "author", request.getAuthor());
                    addIfPresent(uri, "isbn", request.getIsbn());

                    var builtUri = uri.build();
                    log.info("Request URI: {}", builtUri);

                    return uri.build();

                })
                .retrieve()
                .body(BookSearchResponse.class);
    }

private void addIfPresent(UriBuilder uri, String param, String value){
        if (value != null && !value.isBlank()) {
            uri.queryParam(param, value);
        }
    }


}
