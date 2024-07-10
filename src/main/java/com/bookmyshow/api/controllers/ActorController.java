package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.ActorDTO;
import com.bookmyshow.api.models.Actor;
import com.bookmyshow.api.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/actors")
public class ActorController {
    private ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Actor> createActor(@RequestBody ActorDTO request) {
        return ResponseEntity.ok(actorService.createActor(request));
    }
}
