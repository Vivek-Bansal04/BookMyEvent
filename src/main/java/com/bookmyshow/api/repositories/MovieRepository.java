package com.bookmyshow.api.repositories;

import com.bookmyshow.api.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
