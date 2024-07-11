package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.ActorDTO;
import com.bookmyshow.api.exceptions.ResourceNotFoundException;
import com.bookmyshow.api.models.Actor;
import com.bookmyshow.api.repositories.ActorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ActorService {

    private ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor createActor(ActorDTO request) {
        Actor actor = new Actor();
//        actor.setMovies(request.getMovies());
        actor.setName(request.getName());
        return actorRepository.save(actor);
    }

    public Actor findActorById(Long id){
        return actorRepository.findById(id).orElseThrow(() -> {
            log.error("Actor not found with id {}",id);
            throw new ResourceNotFoundException("actor","id",id);
        });
    }
}
