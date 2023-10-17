package com.example.movies.service;

import com.example.movies.dao.GenreRepository;
import com.example.movies.exception.AlreadyExistsException;
import com.example.movies.exception.NotFoundException;
import com.example.movies.mapper.GenreMapper;
import com.example.movies.model.Genre;
import com.example.movies.web.dto.GenreDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository repository;
    private final GenreMapper mapper;


    public GenreService(GenreRepository repository, GenreMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public GenreDto createGenre(GenreDto dto) {
        if (repository.getByName(dto.getName()) != null) {
            throw new AlreadyExistsException("Genre with name '%s' already exists".formatted(dto.getName()));
        }
        Genre genre = repository.create(dto.getName());
        return mapper.toDto(genre);
    }

    public List<GenreDto> getGenres() {
        return repository.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public GenreDto getGenreById(int id) {
        return Optional.ofNullable(repository.getById(id))
                .map(mapper::toDto)
                .orElse(null);
    }

    @Transactional
    public GenreDto updateGenre(Integer id, GenreDto genreDto) {
        Genre byId = repository.getById(id);
        if (byId == null) {
            throw new NotFoundException("Genre with id '%s' not found".formatted(id));
        }
        repository.update(id, genreDto.getName());
        return new GenreDto(id, genreDto.getName());
    }

    @Transactional
    public void deleteGenre(Integer id) {
        Genre byId = repository.getById(id);
        if (byId != null) {
            repository.delete(id);
        }
    }

    public Genre getByName(String genre) {
        return repository.getByName(genre);
    }
}
