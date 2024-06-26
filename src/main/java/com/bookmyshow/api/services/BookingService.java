package com.bookmyshow.api.services;

import com.bookmyshow.api.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.api.models.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    Booking bookTickets(Long showId, List<Long> showSeatIds, Long userId) throws ShowSeatNotAvailableException;
}
