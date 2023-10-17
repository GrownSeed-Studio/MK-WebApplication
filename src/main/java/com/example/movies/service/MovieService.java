package com.example.movies.service;

import com.example.movies.dao.MovieRepository;
import com.example.movies.exception.NotFoundException;
import com.example.movies.mapper.MovieMapper;
import com.example.movies.model.Genre;
import com.example.movies.model.Movie;
import com.example.movies.web.dto.MovieDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreService genreService;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, GenreService genreService) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.genreService = genreService;
    }

    @Transactional
    public MovieDto createMovie(MovieDto movieDto) {
        return movieMapper.toDto(movieRepository.createMovie(movieMapper.fromDto(movieDto)));
    }

    public MovieDto getMovieById(Long id) {
        Movie movieById = movieRepository.getMovieById(id);
        return Optional.ofNullable(movieById)
                .map(movieMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public MovieDto updateMovie(long id, MovieDto movieDto) {
        if (getMovieById(id) == null) {
            throw new NotFoundException("Movie with id '" + id + "' not found");
        }
        return movieMapper.toDto(movieRepository.updateMovie(id, movieMapper.fromDto(movieDto)));
    }

    @Transactional
    public void deleteMovie(long id) {
        if (getMovieById(id) == null) {
            throw new NotFoundException("Movie with id '" + id + "' not found");
        }
        movieRepository.deleteMovie(id);
    }

    public List<MovieDto> findMovies(int pageSize, int pageNumber, String genre) {
        Integer genreId = null;
        if (genre != null) {
            Genre byName = genreService.getByName(genre);
            if (byName == null) {
                throw new NotFoundException("Genre with name '" + genre + "' not found");
            } else {
                genreId = byName.getId();
            }
        }
        return movieRepository.findMovies(pageSize, pageNumber, genreId)
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }
}
