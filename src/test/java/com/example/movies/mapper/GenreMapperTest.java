package com.example.movies.mapper;

import com.example.movies.model.Genre;
import com.example.movies.web.dto.GenreDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenreMapperTest {

    @Test
    void shouldMapEntityToDto() {
        GenreMapper genreMapper = new GenreMapper();
        GenreDto genre = genreMapper.toDto(new Genre(1, "genre"));
        Assertions.assertThat(genre).isEqualTo(new GenreDto(1, "genre"));

    }
}