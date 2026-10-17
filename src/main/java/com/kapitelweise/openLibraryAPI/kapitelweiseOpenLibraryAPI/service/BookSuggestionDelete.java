package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.bookSearchDao;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

@Service
public class BookSuggestionDelete {

    private final bookSearchDao bookSearchDao;

    public BookSuggestionDelete(bookSearchDao bookSearchDao) {
        this.bookSearchDao = bookSearchDao;
    }

    // fixedRate: alle 60.000 Millisekunden wird das ausgeführt und geschaut ob ein Wert älter als 30 Minuten ist.
    @Scheduled(fixedRate = 60000)
    public void deleteBookSuggestions() {
        bookSearchDao.deleteSuggestions();
    }



}
