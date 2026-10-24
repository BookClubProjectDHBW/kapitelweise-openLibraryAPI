package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain;

public class FoundBook {
    private String title;
    private String author;
    private String isbn;
    private Integer pages;
    private Integer firstPublishYear;

    public FoundBook() {
    }

    public FoundBook(String title, String author, String isbn, Integer pages, Integer firstPublishYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
        this.firstPublishYear = firstPublishYear;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }
    public Integer getFirstPublishYear() { return firstPublishYear; }
    public void setFirstPublishYear(Integer firstPublishYear) { this.firstPublishYear = firstPublishYear; }
}
