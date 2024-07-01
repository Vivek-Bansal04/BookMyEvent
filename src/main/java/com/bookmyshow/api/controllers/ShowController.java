package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.ShowDTO;
import com.bookmyshow.api.models.Show;
import com.bookmyshow.api.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/shows")
public class ShowController {

    private ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Show> createShow(
            @RequestBody ShowDTO showDTO
    ) {
        return ResponseEntity.ok(showService.createShow(showDTO));
    }

}
