package com.bookmyshow.api.repositories;

import com.bookmyshow.api.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends
        JpaRepository<Actor, Long> {
}
