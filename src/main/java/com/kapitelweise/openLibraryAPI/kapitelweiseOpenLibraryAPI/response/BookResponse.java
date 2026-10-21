package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "foundBookId",
        "title",
        "author",
        "isbn",
        "pages",
        "firstPublishYear",
        "coverId"
})
public class BookResponse {

    @JsonProperty("foundBookId")
    private Long foundBookId;
    @JsonProperty("title")
    private String title;
    @JsonProperty(value = "author_name", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> authorName;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("pages")
    @JsonAlias("number_of_pages_median")
    private Integer pages;
    @JsonProperty("firstPublishYear")
    @JsonAlias("first_publish_year")
    private Integer firstPublishYear;
    @JsonProperty("coverId")
    @JsonAlias("cover_i")
    private Integer coverId;
    @JsonProperty(value = "editions", access = JsonProperty.Access.WRITE_ONLY)
    private EditionsResponse editions;

    public BookResponse() {
    }

    public Long getFoundBookId() {
        return foundBookId;
    }

    public void setFoundBookId(Long foundBookId) {
        this.foundBookId = foundBookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return authorName == null || authorName.isEmpty() ? null : String.join(", ", authorName);
    }

    public void setAuthorName(List<String> authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    public void setFirstPublishYear(Integer firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }

    public EditionsResponse getEditions() {
        return editions;
    }

    public void setEditions(EditionsResponse editions) {
        this.editions = editions;
    }
}
