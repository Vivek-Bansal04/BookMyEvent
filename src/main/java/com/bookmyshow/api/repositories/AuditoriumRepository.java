package com.bookmyshow.api.repositories;

import com.bookmyshow.api.models.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriumRepository
extends JpaRepository<Auditorium, Long> {
}
