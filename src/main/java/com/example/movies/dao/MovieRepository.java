package com.example.movies.dao;

import com.example.movies.model.Movie;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Movie createMovie(Movie movie) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        Map<String, Object> properties = new HashMap<>();
        properties.put("title", movie.getTitle());
        properties.put("releaseDate", movie.getReleaseDate());
        properties.put("tagline", movie.getTagline());
        properties.put("description", movie.getDescription());
        properties.put("budget", movie.getBudget());
        properties.put("status", movie.getStatus());
        properties.put("revenue", movie.getRevenue());
        properties.put("posterUrl", movie.getPosterUrl());
        jdbcTemplate.update(
                "INSERT INTO movies(title, release_date, tagline, description, budget, status, revenue, poster_url) VALUES (:title, :releaseDate, :tagline, :description, :budget, :status, :revenue, :posterUrl)",
                new MapSqlParameterSource(properties),
                generatedKeyHolder
        );
        Long id = (Long) generatedKeyHolder.getKeys().get("id");
        jdbcTemplate.batchUpdate(
                "INSERT INTO movies_to_genres (movie_id, genre_id) VALUES (:movieId, :genreId)",
                movie.getGenres().stream().map(genreId -> Map.of("movieId", id, "genreId", genreId)).toArray(Map[]::new)
        );
        return new Movie(id, movie.getTitle(), movie.getReleaseDate(), movie.getTagline(), movie.getDescription(), movie.getBudget(), movie.getStatus(), movie.getRevenue(), movie.getPosterUrl(), movie.getGenres());

    }

    public Movie getMovieById(Long id) {
        Movie movie = jdbcTemplate.query(
                "SELECT * FROM movies WHERE id = :id",
                Map.of("id", id),
                resultSet -> {
                    if (resultSet.next()) {
                        return this.map(resultSet);
                    } else {
                        return null;
                    }
                }
        );
        if (movie != null) {
            movie.setGenres(jdbcTemplate.query(
                    "SELECT genre_id FROM movies_to_genres WHERE movie_id = :movieId",
                    Map.of("movieId", id),
                    (resultSet, i) -> resultSet.getInt("genre_id")
            ));
        }
        return movie;
    }

    public Movie updateMovie(long id, Movie movie) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        properties.put("title", movie.getTitle());
        properties.put("releaseDate", movie.getReleaseDate());
        properties.put("tagline", movie.getTagline());
        properties.put("description", movie.getDescription());
        properties.put("budget", movie.getBudget());
        properties.put("status", movie.getStatus());
        properties.put("revenue", movie.getRevenue());
        properties.put("posterUrl", movie.getPosterUrl());
        jdbcTemplate.update(
                "UPDATE movies SET title = :title, release_date = :releaseDate, tagline = :tagline, description = :description, budget = :budget, status = :status, revenue = :revenue, poster_url = :posterUrl WHERE id = :id",
                properties
        );
        jdbcTemplate.update("DELETE FROM movies_to_genres WHERE movie_id = :movieId", Map.of("movieId", id));
        jdbcTemplate.batchUpdate(
                "INSERT INTO movies_to_genres (movie_id, genre_id) VALUES (:movieId, :genreId)",
                movie.getGenres().stream().map(genreId -> Map.of("movieId", id, "genreId", genreId)).toArray(Map[]::new)
        );
        return movie;
    }

    public void deleteMovie(long id) {
        jdbcTemplate.update("DELETE FROM movies_to_genres WHERE movie_id = :movieId", Map.of("movieId", id));
        jdbcTemplate.update("DELETE FROM movies WHERE id = :id", Map.of("id", id));
    }

    public List<Movie> findMovies(int pageSize, int pageNumber, Integer genreId) {
        List<Movie> movies;
        if (genreId == null) {
            movies = jdbcTemplate.query(
                    "SELECT id, title, release_date, tagline, description, budget, status, revenue, poster_url FROM movies LIMIT :pageSize OFFSET :offset",
                    Map.of("pageSize", pageSize, "offset", (pageNumber - 1) * pageSize),
                    (resultSet, i) -> this.map(resultSet)
            );
        } else {
            movies = jdbcTemplate.query(
                    "SELECT DISTINCT m.id, m.title, m.release_date, m.tagline, m.description, m.budget, m.status, m.revenue, m.poster_url FROM movies m JOIN movies_to_genres mg ON m.id = mg.movie_id WHERE mg.genre_id = :genreId LIMIT :pageSize OFFSET :offset",
                    Map.of("pageSize", pageSize, "offset", (pageNumber - 1) * pageSize, "genreId", genreId),
                    (resultSet, i) -> this.map(resultSet)
            );
        }
        if (movies.isEmpty()) {
            return List.of();
        }

        Map<Long, Movie> moviesMap = movies.stream()
                .collect(Collectors.toMap(Movie::getId, e -> e));

        Map<Long, List<Integer>> genres = new HashMap<>();

        jdbcTemplate.query("SELECT movie_id, genre_id FROM movies_to_genres WHERE movie_id IN (:movieIds)",
                Map.of("movieIds", moviesMap.keySet()),
                resultSet -> {
                    Long movieId = resultSet.getLong("movie_id");
                    int genreId1 = resultSet.getInt("genre_id");
                    List<Integer> genres1 = genres.get(movieId);
                    if (genres1 == null) {
                        genres1 = new ArrayList<>();
                    }
                    genres1.add(genreId1);
                    genres.put(movieId, genres1);
                }
        );

        genres.forEach((key, value) -> moviesMap.get(key).setGenres(value));

        return movies;
    }

    private Movie map(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                Optional.ofNullable(resultSet.getDate("release_date")).map(Date::toLocalDate).orElse(null),
                resultSet.getString("tagline"),
                resultSet.getString("description"),
                resultSet.getObject("budget", Integer.class),
                resultSet.getString("status"),
                resultSet.getObject("revenue", Double.class),
                resultSet.getString("poster_url"),
                null
        );
    }
}
