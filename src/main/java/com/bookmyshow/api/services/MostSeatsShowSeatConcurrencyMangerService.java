package com.bookmyshow.api.services;

import com.bookmyshow.api.models.ShowSeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostSeatsShowSeatConcurrencyMangerService implements ShowSeatConcurrencyManagerService{
    private List<List<Long>> showSeatsRequestCollection = new ArrayList<>();
    private Map<Long,Integer> countofLockRequestsForSeatIds = new HashMap<>();

    @Override
    public boolean getLockOverShowSeats(List<ShowSeat> showSeatList) {
        //for all seats check if any of them has more than 1 request;
        // if yes then check size of seats
        return false;
    }
}
