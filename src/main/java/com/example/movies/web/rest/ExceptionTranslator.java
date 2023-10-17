package com.example.movies.web.rest;

import com.example.movies.exception.AlreadyExistsException;
import com.example.movies.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> translateException(AlreadyExistsException alreadyExistsException) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", alreadyExistsException.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> translateException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus. NOT_FOUND)
                .body(Map.of("message", e.getMessage()));
    }

}
