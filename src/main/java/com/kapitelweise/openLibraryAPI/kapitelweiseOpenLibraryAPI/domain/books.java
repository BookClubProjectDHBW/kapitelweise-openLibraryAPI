package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"ia", "ia_collection"})
@JsonPropertyOrder({
        "cover_i",
        "has_fulltext",
        "edition_count",
        "title",
        "isbn",
        "number_of_pages_median",
        "author_name",
        "first_publish_year",
        "key",
        "author_key",
        "public_scan_b"
})
@Generated("jsonschema2pojo")
public class books {

    @JsonProperty("cover_i")
    private Integer coverI;
    @JsonProperty("has_fulltext")
    private Boolean hasFulltext;
    @JsonProperty("edition_count")
    private Integer editionCount;
    @JsonProperty("title")
    private String title;
    @JsonProperty("isbn")
    private List<String> isbn;
    @JsonProperty("number_of_pages_median")
    private Integer numberOfPagesMedian;
    @JsonProperty("author_name")
    private List<String> authorName;
    @JsonProperty("first_publish_year")
    private Integer firstPublishYear;
    @JsonProperty("key")
    private String key;
    @JsonProperty("author_key")
    private List<String> authorKey;
    @JsonProperty("public_scan_b")
    private Boolean publicScanB;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public books() {
    }

    public books(Integer coverI, Boolean hasFulltext, Integer editionCount, String title, List<String> isbn, Integer numberOfPagesMedian, List<String> authorName, Integer firstPublishYear, String key, List<String> authorKey, Boolean publicScanB) {
        super();
        this.coverI = coverI;
        this.hasFulltext = hasFulltext;
        this.editionCount = editionCount;
        this.title = title;
        this.isbn = isbn;
        this.numberOfPagesMedian = numberOfPagesMedian;
        this.authorName = authorName;
        this.firstPublishYear = firstPublishYear;
        this.key = key;
        this.authorKey = authorKey;
        this.publicScanB = publicScanB;
    }

    @JsonProperty("cover_i")
    public Integer getCoverI() {
        return coverI;
    }

    @JsonProperty("cover_i")
    public void setCoverI(Integer coverI) {
        this.coverI = coverI;
    }

    @JsonProperty("has_fulltext")
    public Boolean getHasFulltext() {
        return hasFulltext;
    }

    @JsonProperty("has_fulltext")
    public void setHasFulltext(Boolean hasFulltext) {
        this.hasFulltext = hasFulltext;
    }

    @JsonProperty("edition_count")
    public Integer getEditionCount() {
        return editionCount;
    }

    @JsonProperty("edition_count")
    public void setEditionCount(Integer editionCount) {
        this.editionCount = editionCount;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("isbn")
    public List<String> getIsbn() {
        return isbn;
    }

    @JsonProperty("isbn")
    public void setIsbn(List<String> isbn) {
        this.isbn = isbn;
    }

    @JsonProperty("number_of_pages_median")
    public Integer getNumberOfPagesMedian() {
        return numberOfPagesMedian;
    }

    @JsonProperty("number_of_pages_median")
    public void setNumberOfPagesMedian(Integer numberOfPagesMedian) {
        this.numberOfPagesMedian = numberOfPagesMedian;
    }

    @JsonProperty("author_name")
    public List<String> getAuthorName() {
        return authorName;
    }

    @JsonProperty("author_name")
    public void setAuthorName(List<String> authorName) {
        this.authorName = authorName;
    }

    @JsonProperty("first_publish_year")
    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    @JsonProperty("first_publish_year")
    public void setFirstPublishYear(Integer firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }


    @JsonProperty("author_key")
    public List<String> getAuthorKey() {
        return authorKey;
    }

    @JsonProperty("author_key")
    public void setAuthorKey(List<String> authorKey) {
        this.authorKey = authorKey;
    }

    @JsonProperty("public_scan_b")
    public Boolean getPublicScanB() {
        return publicScanB;
    }

    @JsonProperty("public_scan_b")
    public void setPublicScanB(Boolean publicScanB) {
        this.publicScanB = publicScanB;
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
        sb.append(books.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("coverI");
        sb.append('=');
        sb.append(((this.coverI == null)?"<null>":this.coverI));
        sb.append(',');
        sb.append("hasFulltext");
        sb.append('=');
        sb.append(((this.hasFulltext == null)?"<null>":this.hasFulltext));
        sb.append(',');
        sb.append("editionCount");
        sb.append('=');
        sb.append(((this.editionCount == null)?"<null>":this.editionCount));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("isbn");
        sb.append('=');
        sb.append(((this.isbn == null)?"<null>":this.isbn));
        sb.append(',');
        sb.append("numberOfPagesMedian");
        sb.append('=');
        sb.append(((this.numberOfPagesMedian == null)?"<null>":this.numberOfPagesMedian));
        sb.append(',');
        sb.append("authorName");
        sb.append('=');
        sb.append(((this.authorName == null)?"<null>":this.authorName));
        sb.append(',');
        sb.append("firstPublishYear");
        sb.append('=');
        sb.append(((this.firstPublishYear == null)?"<null>":this.firstPublishYear));
        sb.append(',');
        sb.append("key");
        sb.append('=');
        sb.append(((this.key == null)?"<null>":this.key));
        sb.append(',');
        sb.append("authorKey");
        sb.append('=');
        sb.append(((this.authorKey == null)?"<null>":this.authorKey));
        sb.append(',');
        sb.append("publicScanB");
        sb.append('=');
        sb.append(((this.publicScanB == null)?"<null>":this.publicScanB));
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
