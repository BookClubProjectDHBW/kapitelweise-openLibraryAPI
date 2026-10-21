package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "docs"
})
public class BookSearchResponse {

    @JsonProperty("docs")
    private List<BookResponse> docs;

    public BookSearchResponse() {
    }

    @JsonProperty("docs")
    public List<BookResponse> getDocs() {
        return docs;
    }

    @JsonProperty("docs")
    public void setDocs(List<BookResponse> docs) {
        this.docs = docs;
    }
}
