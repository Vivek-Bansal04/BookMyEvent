package com.bookmyshow.api.services;

import com.bookmyshow.api.models.ShowSeat;

import java.util.List;

public interface ShowSeatConcurrencyManagerService {
    boolean getLockOverShowSeats(List<ShowSeat> showSeatList);
}
