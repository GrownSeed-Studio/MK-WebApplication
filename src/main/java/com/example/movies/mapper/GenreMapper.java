package com.example.movies.mapper;

import com.example.movies.model.Genre;
import com.example.movies.web.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

}
