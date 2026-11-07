package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.FoundBook;

import java.util.Optional;

public interface BookSearchDao {

    Long saveFoundBook(String title, String author, String isbn, Integer pages, Integer years_of_publishing);
    void deleteFoundBooks();
    Optional<FoundBook> getDetails(String isbn);
}
