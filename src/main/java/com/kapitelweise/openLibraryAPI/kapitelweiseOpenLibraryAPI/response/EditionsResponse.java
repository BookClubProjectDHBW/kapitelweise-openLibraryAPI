package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EditionsResponse {
    @JsonProperty("docs")
    private List<EditionResponse> docs;

    public List<EditionResponse> getDocs() { return docs; }
    public void setDocs(List<EditionResponse> docs) { this.docs = docs; }
}
