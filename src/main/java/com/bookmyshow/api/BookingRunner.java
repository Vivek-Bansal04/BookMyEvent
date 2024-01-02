package com.bookmyshow.api;

import com.bookmyshow.api.controllers.BookingController;
import com.bookmyshow.api.dtos.CreateBookingRequestDTO;

import java.util.List;


public class BookingRunner implements Runnable {

    private BookingController ticketController;
    private Long showId;
    private List<Long> showSeatId;
    private Long userId;

    public BookingRunner(BookingController ticketController,
                         Long showId,
                         List<Long> showSeatId,
                         Long userId) {
        this.ticketController = ticketController;
        this.showId = showId;
        this.showSeatId = showSeatId;
        this.userId = userId;
    }
    @Override
    public void run() {
        try {
            CreateBookingRequestDTO requestDTO = new CreateBookingRequestDTO();
            requestDTO.setShowId(showId);
            requestDTO.setShowSeatIds(showSeatId);
            requestDTO.setUserId(userId);
            this.ticketController.bookTicket(requestDTO);
        } catch (Exception exception) {
            System.out.println("EXCEPTIOOOOOOOOONNNN: " + exception.getMessage());
        }

    }
}
