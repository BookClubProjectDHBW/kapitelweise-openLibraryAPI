package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class BookSuggestionDelete {

    private final JdbcTemplate jdbcTemplate;

    public BookSuggestionDelete(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedRate = 60000)
    public void deleteSuggestions() {
        jdbcTemplate.update("""
            DELETE FROM book_suggestions WHERE saved_at < NOW() - INTERVAL '20 minutes'
            """);
    }


}
