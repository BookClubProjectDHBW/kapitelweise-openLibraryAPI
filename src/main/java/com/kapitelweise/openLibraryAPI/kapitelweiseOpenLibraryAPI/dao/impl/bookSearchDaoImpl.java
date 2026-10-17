package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.impl;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.bookSearchDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class bookSearchDaoImpl implements bookSearchDao {

    private final JdbcTemplate jdbcTemplate;

    public bookSearchDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveBookSuggestions(String title, String author, String isbn, Integer pages, Integer years_of_publication){
        String sql = """
                insert into book_suggestions
                (title, author, isbn, pages,year_of_publishing)
                values(?,?,?,?,?)
                on conflict (isbn) do nothing
                """;

        jdbcTemplate.update(
                sql,
                title,
                author,
                isbn,
                pages,
                years_of_publication
        );
    }

    @Override
    public void deleteSuggestions() {
        jdbcTemplate.update("""
            DELETE FROM book_suggestions WHERE saved_at < NOW() - INTERVAL '30 minutes'
            """);
    }

}




