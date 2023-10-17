package com.example.movies.web.rest;

import com.example.movies.service.MovieService;
import com.example.movies.web.dto.MovieDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies(
            @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) @Max(20) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(value = "genre", required = false) String genre
    ) {
        return ResponseEntity.ok(
                movieService.findMovies(pageSize, pageNumber, genre)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable("id") Long id) {
        return Optional.ofNullable(movieService.getMovieById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody @Valid MovieDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.createMovie(movieDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable("id") Integer id, @RequestBody @Valid MovieDto movieDto) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
