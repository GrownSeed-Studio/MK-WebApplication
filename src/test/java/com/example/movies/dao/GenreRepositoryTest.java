package com.example.movies.dao;

import com.zaxxer.hikari.util.DriverDataSource;
import org.assertj.db.api.Assertions;
import org.assertj.db.type.Changes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.Properties;

public class GenreRepositoryTest extends BaseDBTest {

    GenreRepository genreRepository;
    private DriverDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DriverDataSource(container.getJdbcUrl(), Driver.class.getCanonicalName(), new Properties(), container.getUsername(), container.getPassword());
        genreRepository = new GenreRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @Test
    void shouldCreateGenre() {
        String genre = RandomStringUtils.randomAlphabetic(5);
        Changes changes = new Changes(dataSource);
        changes.setStartPointNow();
        genreRepository.create(genre);
        changes.setEndPointNow();
        Assertions.assertThat(changes)
                .hasNumberOfChanges(1)
                .changeOfCreation()
                .isOnTable("genres")
                .hasModifiedColumns("id", "name")
                .column("name").valueAtEndPoint().isEqualTo(genre);
    }
}