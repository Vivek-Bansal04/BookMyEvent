package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.BookingRequestDTO;
import com.bookmyshow.api.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.api.models.Booking;
import com.bookmyshow.api.services.BookingService;
import com.bookmyshow.api.utils.TLResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Booking> bookTicket(
            @RequestBody BookingRequestDTO request
    ) throws ShowSeatNotAvailableException {
        return ResponseEntity.ok(
                bookingService.bookTickets(
                        request.getShowId() ,request.getShowSeatIds(), request.getUserId())
        );
    }

    @GetMapping(path = "/check/health",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> health() {
        return ResponseEntity.ok().build();
    }
}
