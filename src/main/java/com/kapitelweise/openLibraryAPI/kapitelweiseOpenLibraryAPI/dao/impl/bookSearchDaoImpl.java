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
    public Long saveFoundBook(String title, String author, String isbn, Integer pages, Integer years_of_publication){
        String sql = """
                insert into found_book
                (title, author, isbn, pages,year_of_publishing)
                values(?,?,?,?,?)
                on conflict (isbn) do update
                set isbn = excluded.isbn
                returning found_book_id;
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                title,
                author,
                isbn,
                pages,
                years_of_publication
        );
    }

    @Override
    public void deleteFoundBooks() {
        jdbcTemplate.update("""
            DELETE FROM found_book WHERE saved_at < NOW() - INTERVAL '10 minutes'
            """);
    }

}



