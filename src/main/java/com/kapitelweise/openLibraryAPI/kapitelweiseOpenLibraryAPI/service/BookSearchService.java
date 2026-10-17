package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.bookSearchDao;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.books;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriBuilder;

import java.net.http.HttpClient;
import java.time.Duration;


@Service
public class BookSearchService {

    private final bookSearchDao bookSearchDao;

    public BookSearchService(bookSearchDao bookSearchDao) {
        this.bookSearchDao = bookSearchDao;
    }


    private final RestClient restClient = RestClient.builder()
            .requestFactory(requestFactory())
            .build();

    private final String lang = "ger";

private static final Logger log = LoggerFactory.getLogger(BookSearchService.class);

    public BookSearchResponse searchBooks(BookSearchRequest request) {
       try {
           BookSearchResponse response = restClient.get()
                   .uri(uriBuilder -> {
                       var uri = uriBuilder
                               .scheme("https")
                               .host("openlibrary.org")
                               .path("/search.json")
                               .queryParam("limit", 10)
                               .queryParam("language", lang);

                       addIfPresent(uri, "q", request.getQ());
                       addIfPresent(uri, "title", request.getTitle());
                       addIfPresent(uri, "author", request.getAuthor());
                       addIfPresent(uri, "isbn", request.getIsbn());

                       var builtUri = uri.build();
                       log.info("Request URI: {}", builtUri);

                       return uri.build();

                   })
                   .retrieve()
                   .body(BookSearchResponse.class);



           //Ruft DAO auf
           if (response != null && response.getDocs() != null) {
               for (books book : response.getDocs()) {
                   if (book.getTitle() == null || book.getTitle().isBlank()) {
                       continue;
                   }
                   bookSearchDao.saveBookSuggestions(
                           book.getTitle(),
                           book.getAuthorName() == null || book.getAuthorName().isEmpty()
                           ? null
                           : book.getAuthorName().get(0),
                           book.getIsbn() == null || book.getIsbn().isEmpty()
                           ? null
                           : book.getIsbn().get(0),
                           book.getNumberOfPagesMedian(),
                           book.getFirstPublishYear()
                   );
               }
           }
           return response;
       }
       catch(ResourceAccessException e){
           throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "OpenLibrary  not found");
       }
    }

private void addIfPresent(UriBuilder uri, String param, String value){
        if (value != null && !value.isBlank()) {
            uri.queryParam(param, value);
        }
    }


    private static JdkClientHttpRequestFactory requestFactory() {
        var httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(7))
                .build();
        var factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(7));
        return factory;
    }






}
