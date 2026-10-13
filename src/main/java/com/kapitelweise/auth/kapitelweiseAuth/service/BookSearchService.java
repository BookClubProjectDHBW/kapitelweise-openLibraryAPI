package com.kapitelweise.auth.kapitelweiseAuth.service;

import com.kapitelweise.auth.kapitelweiseAuth.request.BookSearchRequest;
import com.kapitelweise.auth.kapitelweiseAuth.response.BookSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;


@Service
public class BookSearchService {


    private final RestClient restClient = RestClient.create();

    public BookSearchResponse searchBooks(BookSearchRequest request){
        return restClient.get()
                .uri(uriBuilder -> {
                    var uri = uriBuilder
                            .scheme("https")
                            .host("openlibrary.org")
                            .path("/search.json");

                    addIfPresent(uri,"q", request.getQ());
                    addIfPresent(uri, "title", request.getTitle());
                    addIfPresent(uri, "author", request.getAuthor());
                    addIfPresent(uri, "isbn", request.getIsbn());
                    addIfPresent(uri, "page", request.getPage());


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
