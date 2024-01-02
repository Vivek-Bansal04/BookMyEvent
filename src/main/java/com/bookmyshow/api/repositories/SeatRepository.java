package com.bookmyshow.api.repositories;

import com.bookmyshow.api.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository
extends JpaRepository<Seat, Long> {
}
