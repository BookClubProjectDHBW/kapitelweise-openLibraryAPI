package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain;

public record SuggestionEvent(String type, Long suggestionId, Long bookClubId, String isbn) {
}

