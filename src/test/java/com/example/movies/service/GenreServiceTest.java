package com.example.movies.service;

import com.example.movies.dao.GenreRepository;
import com.example.movies.mapper.GenreMapper;
import com.example.movies.model.Genre;
import com.example.movies.web.dto.GenreDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GenreServiceTest {

    private GenreRepository genreRepository;
    private GenreMapper genreMapper;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreRepository = mock(GenreRepository.class);
        genreMapper = mock(GenreMapper.class);
        genreService = new GenreService(genreRepository, genreMapper);
    }

    @Test
    void shouldCreateGenre() {
        when(genreRepository.getByName("genre")).thenReturn(null);
        when(genreMapper.toDto(new Genre(1, "genre"))).thenReturn(new GenreDto(1, "genre"));
        when(genreRepository.create("genre")).thenReturn(new Genre(1, "genre"));

        GenreDto genre = genreService.createGenre(new GenreDto(null, "genre"));

        Assertions.assertThat(genre).isEqualTo(new GenreDto(1, "genre"));

        verify(genreRepository, times(1)).create("genre");
    }
}