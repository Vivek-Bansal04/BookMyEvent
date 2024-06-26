package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.BookingRequestDTO;
import com.bookmyshow.api.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.api.models.Booking;
import com.bookmyshow.api.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/book/tickets")
    public Booking bookTicket(BookingRequestDTO request) throws ShowSeatNotAvailableException {
        return bookingService.bookTickets(
                request.getShowId() ,request.getShowSeatIds(), request.getUserId()
        );
    }
}
