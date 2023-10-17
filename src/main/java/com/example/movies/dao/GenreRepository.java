package com.example.movies.dao;

import com.example.movies.model.Genre;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GenreRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GenreRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre create(String name) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                "INSERT INTO genres(name) VALUES (:name)",
                new MapSqlParameterSource("name", name),
                generatedKeyHolder
        );
        Number id = (Number) generatedKeyHolder.getKeys().get("id");
        return new Genre(id.intValue(), name);
    }

    public Genre getByName(String name) {
        return jdbcTemplate.query("SELECT id, name FROM genres WHERE name = :name", new MapSqlParameterSource("name", name), rs -> {
            if (rs.next()) {
                return new Genre(rs.getInt("id"), rs.getString("name"));
            } else {
                return null;
            }
        });
    }

    public List<Genre> getAll() {
        return jdbcTemplate.query("SELECT id, name FROM genres", (rs, i) -> new Genre(rs.getInt("id"), rs.getString("name")));
    }

    public Genre getById(int id) {
        return jdbcTemplate.query("SELECT id, name FROM genres WHERE id = :id", new MapSqlParameterSource("id", id), rs -> {
            if (rs.next()) {
                return new Genre(rs.getInt("id"), rs.getString("name"));
            } else {
                return null;
            }
        });
    }

    public void update(int id, String name) {
        jdbcTemplate.update("UPDATE genres SET name = :name WHERE id = :id", new MapSqlParameterSource(Map.of("name", name, "id", id)));
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM genres WHERE id = :id", new MapSqlParameterSource("id", id));
    }
}
