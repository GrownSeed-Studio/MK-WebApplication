package com.example.movies.mapper;

import com.example.movies.dao.GenreRepository;
import com.example.movies.exception.NotFoundException;
import com.example.movies.model.Genre;
import com.example.movies.model.Movie;
import com.example.movies.web.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieMapper {

    private final GenreRepository genreRepository;

    public MovieMapper(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Movie fromDto(MovieDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setTagline(dto.getTagline());
        movie.setDescription(dto.getDescription());
        movie.setBudget(dto.getBudget());
        movie.setStatus(dto.getStatus());
        movie.setRevenue(dto.getRevenue());
        movie.setPosterUrl(dto.getPosterUrl());
        movie.setGenres(dto.getGenres()
                .stream()
                .map(name -> {
                            Genre byName = genreRepository.getByName(name);
                            if (byName == null) {
                                throw new NotFoundException("Genre with name '" + name + "' not found");
                            } else {
                                return byName.getId();
                            }
                        }
                )
                .collect(Collectors.toList()));
        return movie;
    }

    public MovieDto toDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setTagline(movie.getTagline());
        movieDto.setDescription(movie.getDescription());
        movieDto.setBudget(movie.getBudget());
        movieDto.setStatus(movie.getStatus());
        movieDto.setRevenue(movie.getRevenue());
        movieDto.setPosterUrl(movie.getPosterUrl());
        movieDto.setGenres(movie.getGenres()
                .stream()
                .map(genreRepository::getById)
                .map(Genre::getName)
                .collect(Collectors.toList()));
        return movieDto;
    }
}
