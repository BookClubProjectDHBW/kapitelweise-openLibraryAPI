package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain;

public record SuggestionResultEvent(String type, Long suggestionId, String status, String title, String author, String isbn, Integer pages, Integer firstPublishYear) {
}
