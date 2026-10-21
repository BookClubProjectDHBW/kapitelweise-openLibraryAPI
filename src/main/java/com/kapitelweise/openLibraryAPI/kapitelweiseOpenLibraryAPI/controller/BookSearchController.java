package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.controller;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service.BookSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookSearchController {

    private final BookSearchService bookSearchService;
    public BookSearchController(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }


    @GetMapping("/book-search")
    public BookSearchResponse getBookSearch(BookSearchRequest request) {
        return bookSearchService.searchBooks(request);
    }


}
