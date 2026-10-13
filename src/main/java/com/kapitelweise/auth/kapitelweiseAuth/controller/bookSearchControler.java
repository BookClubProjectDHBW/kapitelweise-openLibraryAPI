package com.kapitelweise.auth.kapitelweiseAuth.controller;

import com.kapitelweise.auth.kapitelweiseAuth.request.BookSearchRequest;
import com.kapitelweise.auth.kapitelweiseAuth.response.BookSearchResponse;
import com.kapitelweise.auth.kapitelweiseAuth.service.BookSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bookSearchControler {

    private final BookSearchService bookSearchService;

    public bookSearchControler(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }


    @GetMapping("/bookSearch")
    public BookSearchResponse bookSearch(BookSearchRequest request) {
        return bookSearchService.searchBooks(request);
    }


}
