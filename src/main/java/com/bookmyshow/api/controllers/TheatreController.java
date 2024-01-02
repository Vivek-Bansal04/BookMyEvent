package com.bookmyshow.api.controllers;

import com.bookmyshow.api.exceptions.CityNotFoundException;
import com.bookmyshow.api.models.SeatType;
import com.bookmyshow.api.models.Theatre;
import com.bookmyshow.api.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class TheatreController {
    private TheatreService theatreService;

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public Theatre createTheatre(
            String name,
            String address,
            Long cityId
    ) {
        Theatre theatre = null;
        try {
            theatre = this.theatreService.createTheatre(name, address, cityId);
        } catch (CityNotFoundException e) {
            System.out.println("Something wrong happened");
        }

        return theatre;
    }

    public Theatre addAuditorium(Long theatreId, String name,
                                 int capacity) {
        return theatreService.addAuditorium(theatreId, name, capacity);
    }

    /**
     * In which auditorium
     * you want to add how many seats
     * of what type
     * @param auditoriumId
     * @param seatCount
     */
    public void addSeats(
            Long auditoriumId,
            Map<SeatType, Integer> seatCount
    ) {
        theatreService.addSeats(auditoriumId, seatCount);
    }
}
