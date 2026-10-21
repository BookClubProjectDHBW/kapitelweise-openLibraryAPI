package com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.impl;

import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.dao.BookSearchDao;
import com.kapitelweise.openLibraryAPI.kapitelweiseOpenLibraryAPI.domain.FoundBook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class BookSearchDaoImpl implements BookSearchDao {

    private final JdbcTemplate jdbcTemplate;

    public BookSearchDaoImpl(JdbcTemplate jdbcTemplate) {
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

   @Override
    public Optional<FoundBook> getDetails(String isbn){

        String sql = "Select title, author, isbn, pages, year_of_publishing from found_book where isbn = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                                new FoundBook(
                                        rs.getString("title"),
                                        rs.getString("author"),
                                        rs.getString("isbn"),
                                        rs.getObject("pages", Integer.class),
                                        rs.getObject("year_of_publishing", Integer.class)
                                ),
                        isbn)
                .stream()
                .findFirst();
    }

}



