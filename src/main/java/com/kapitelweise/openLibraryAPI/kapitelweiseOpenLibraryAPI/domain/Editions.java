package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Editions {
    @JsonProperty("numFound")
    private Integer numFound;

    @JsonProperty("docs")
    private List<Edition> docs;

    public Integer getNumFound() { return numFound; }
    public void setNumFound(Integer numFound) { this.numFound = numFound; }
    public List<Edition> getDocs() { return docs; }
    public void setDocs(List<Edition> docs) { this.docs = docs; }
}
