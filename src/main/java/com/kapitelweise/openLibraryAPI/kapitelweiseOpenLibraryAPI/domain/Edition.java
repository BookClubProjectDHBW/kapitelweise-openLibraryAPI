package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Edition {
    @JsonProperty("isbn")
    private List<String> isbn;

    public List<String> getIsbn() { return isbn; }
    public void setIsbn(List<String> isbn) { this.isbn = isbn; }
}
