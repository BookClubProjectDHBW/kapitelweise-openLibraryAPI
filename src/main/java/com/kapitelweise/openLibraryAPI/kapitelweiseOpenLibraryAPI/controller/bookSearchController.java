package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.controller;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service.BookSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bookSearchController {

    private final BookSearchService bookSearchService;
    public bookSearchController(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }


    @GetMapping("/bookSearch")
    public BookSearchResponse getBookSearch(BookSearchRequest request) {
        return bookSearchService.searchBooks(request);
    }


}
