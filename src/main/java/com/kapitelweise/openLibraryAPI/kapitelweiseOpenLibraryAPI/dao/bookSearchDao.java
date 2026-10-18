package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.books;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.request.BookSearchRequest;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response.BookSearchResponse;

public interface bookSearchDao {

    public Long saveFoundBook(String title, String author, String isbn, Integer pages, Integer years_of_publishing);
    void deleteFoundBooks();
}
