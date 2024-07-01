package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.AuditoriumDTO;
import com.bookmyshow.api.dtos.AuditoriumSeatDTO;
import com.bookmyshow.api.dtos.TheatreDTO;
import com.bookmyshow.api.exceptions.CityNotFoundException;
import com.bookmyshow.api.models.SeatType;
import com.bookmyshow.api.models.Theatre;
import com.bookmyshow.api.services.TheatreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/theatres")
@Slf4j
public class TheatreController {
    private TheatreService theatreService;

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Theatre> createTheatre(@RequestBody TheatreDTO request) {
        try {
            return ResponseEntity.ok(
                    theatreService.createTheatre(
                            request.getName(), request.getAddress(), request.getCityId()
                    )
            );
        } catch (CityNotFoundException e) {
            log.error("City not found with id{}",request.getCityId());
        }catch (Exception e){
            log.error("Error in creating theatre",e);
        }
        return null;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Theatre>> getTheatres() {
        return ResponseEntity.ok(theatreService.getTheatres());
    }

    @PostMapping(path = "/auditorium/{theatreId}", produces = "application/json")
    public ResponseEntity<Theatre> addAuditorium(
            @PathVariable(value = "theatreId") Long theatreId,
            @RequestBody AuditoriumDTO request
    ) {
        return ResponseEntity.ok(
                theatreService.addAuditorium(theatreId, request.getName(), request.getCapacity())
        );
    }

    /**
     * In which auditorium
     * you want to add how many seats
     * of what type
     */
    @PostMapping(path = "/auditorium/{auditoriumId}/seats", produces = "application/json")
    public void addSeats(
            @PathVariable(value = "auditoriumId") Long auditoriumId,
            @RequestBody AuditoriumSeatDTO request
    ) {
        theatreService.addSeats(auditoriumId, request.getSeatCount());
    }
}
