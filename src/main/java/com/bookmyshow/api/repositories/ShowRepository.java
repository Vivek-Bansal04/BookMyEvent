package com.bookmyshow.api.repositories;

import com.bookmyshow.api.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository
extends JpaRepository<Show, Long> {


    Optional<Show> findById(Long id);
}
