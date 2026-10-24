package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.BookSearchDao;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookResponse;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
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

    private final BookSearchDao bookSearchDao;

    public BookSearchService(BookSearchDao bookSearchDao) {
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
                               .queryParam("language", lang)
                               .queryParam("fields", "key,title,author_name,first_publish_year,number_of_pages_median,cover_i,editions,editions.isbn");

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
               for (BookResponse book : response.getDocs()) {
                   String isbn = firstEditionIsbn(book);
                   if (book.getTitle() == null || book.getTitle().isBlank() || isbn == null || isbn.isBlank()) {
                       continue;
                   }
                   Long id = bookSearchDao.saveFoundBook(
                           book.getTitle(),
                           book.getAuthor(),
                           isbn,
                           book.getPages(),
                           book.getFirstPublishYear()
                   );
                   book.setFoundBookId(id);
                   book.setIsbn(isbn);
               }
               response.getDocs().removeIf(book -> book.getFoundBookId() == null);
           }
           return response;
       }
       catch(ResourceAccessException e){
           throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "OpenLibrary  not found");
       }
    }
    private String firstEditionIsbn(BookResponse book) {
        if (book.getEditions() == null || book.getEditions().getDocs() == null) {
            return null;
        }
        for (var edition : book.getEditions().getDocs()) {
            if (edition.getIsbn() != null && !edition.getIsbn().isEmpty()) {
                return edition.getIsbn().get(0);
            }
        }
        return null;
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
