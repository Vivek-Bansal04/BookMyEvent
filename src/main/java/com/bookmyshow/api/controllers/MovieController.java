package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.MovieDTO;
import com.bookmyshow.api.models.Movie;
import com.bookmyshow.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Movie> createMovie(@RequestBody MovieDTO request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }
}