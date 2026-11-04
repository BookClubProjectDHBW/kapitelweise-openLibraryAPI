package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.BookSearchDao;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

@Service
public class FoundBookDelete {

    private final BookSearchDao bookSearchDao;

    public FoundBookDelete(BookSearchDao bookSearchDao) {
        this.bookSearchDao = bookSearchDao;
    }

    // fixedRate: alle 60.000 Millisekunden wird das ausgeführt und geschaut ob ein Wert älter als 10 Minuten ist.
    @Scheduled(fixedRate = 60000)
    public void deleteFoundBooks() {
        bookSearchDao.deleteFoundBooks();
    }



}
