package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        var params = Collections.singletonMap("id", id);
        try {
            var book = jdbc.queryForObject(
                    "select books.id, books.title, books.author_id, authors.full_name, books.genre_id, genres.name from books " +
                            "inner join authors on authors.id = books.author_id " +
                            "inner join genres on genres.id = books.genre_id " +
                            "where books.id = :id",
                    params,
                    new BookRowMapper()
            );
            return Optional.ofNullable(book);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error("Book not found: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(
                "select books.id, books.title, books.author_id, authors.full_name, books.genre_id, genres.name from books " +
                        "inner join authors on authors.id = books.author_id " +
                        "inner join genres on genres.id = books.genre_id",
                new BookRowMapper()
        );
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from books where id = :id",
                params
        );
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        var params = Map.of(
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId());

        var mapSqlParameterSource = new MapSqlParameterSource(params);

        jdbc.update(
                "insert into books(title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                mapSqlParameterSource,
                keyHolder,
                new String[]{"id"}
        );

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        var params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId());

        var rowNum = jdbc.update(
                "update books set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                params
        );

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        if (rowNum == 0) {
            throw new EntityNotFoundException("Не обновлено ни одной записи в БД");
        }

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var title = rs.getString("title");
            var authorId = rs.getLong("author_id");
            var authorFullName = rs.getString("full_name");
            var genreId = rs.getLong("genre_id");
            var genreName = rs.getString("name");
            return new Book(id, title, new Author(authorId, authorFullName), new Genre(genreId, genreName));
        }
    }
}
