package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.books;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "start",
        "num_found",
        "docs"
})
@Generated("jsonschema2pojo")
public class BookSearchResponse {

    @JsonProperty("start")
    private Integer start;
    @JsonProperty("num_found")
    private Integer numFound;
    @JsonProperty("docs")
    private List<books> docs;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public BookSearchResponse() {
    }

    public BookSearchResponse(Integer start, Integer numFound, List<books> docs) {
        super();
        this.start = start;
        this.numFound = numFound;
        this.docs = docs;
    }

    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    @JsonProperty("num_found")
    public Integer getNumFound() {
        return numFound;
    }

    @JsonProperty("num_found")
    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    @JsonProperty("docs")
    public List<books> getDocs() {
        return docs;
    }

    @JsonProperty("docs")
    public void setDocs(List<books> docs) {
        this.docs = docs;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BookSearchResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("start");
        sb.append('=');
        sb.append(((this.start == null)?"<null>":this.start));
        sb.append(',');
        sb.append("numFound");
        sb.append('=');
        sb.append(((this.numFound == null)?"<null>":this.numFound));
        sb.append(',');
        sb.append("docs");
        sb.append('=');
        sb.append(((this.docs == null)?"<null>":this.docs));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
