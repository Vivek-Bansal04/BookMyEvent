package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.MovieDTO;
import com.bookmyshow.api.exceptions.ResourceNotFoundException;
import com.bookmyshow.api.models.Actor;
import com.bookmyshow.api.models.Movie;
import com.bookmyshow.api.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorService actorService;

    public MovieService(
            MovieRepository movieRepository, ActorService actorService){
        this.movieRepository = movieRepository;
        this.actorService = actorService;
    }

    public Movie createMovie(MovieDTO request){
        Movie movie = new Movie();
        movie.setMovieFeatures(request.getMovieFeatures());
        movie.setName(request.getName());
        movie.setRating(request.getRating());
        movie.setLength(request.getLength());
        movie.setLanguages(request.getLanguages());
        List<Actor> actors = request.getActors().stream()
                .map(actorId -> actorService.findActorById(actorId))
                .collect(Collectors.toList());
        movie.setActors(actors);
        return movieRepository.save(movie);
    }

    public Movie findMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> {
            log.error("Movie not found with id {}", id);
            return new ResourceNotFoundException("Movie", "id", id);
        });
    }

}
