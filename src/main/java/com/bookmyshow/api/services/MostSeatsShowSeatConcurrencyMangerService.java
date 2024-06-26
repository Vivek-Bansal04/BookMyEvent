package com.bookmyshow.api.services;

import com.bookmyshow.api.models.ShowSeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class MostSeatsShowSeatConcurrencyMangerService implements ShowSeatConcurrencyManagerService {
    ConcurrentHashMap<Long, Integer> isShowSeatLocked = new ConcurrentHashMap<>();
    //seat and timestamp when seat was locked
    private Map<Long, Long> showSeatsRequestCollection = new ConcurrentHashMap<>();
    private Map<Long, Integer> countofLockRequestsForSeatIds = new ConcurrentHashMap<>();


    @Override
    public boolean getLockOverShowSeats(List<Long> showSeatList) {
//        Long currentTime = System.currentTimeMillis();
//        for (Long showSeatId : showSeatList) {
//            synchronized (showSeatId) {
//                if (isShowSeatLocked.containsKey(showSeatId) && isShowSeatLocked.get(showSeatId).equals(1)) {
//                    //here we can still check bcoz other user might have booked less than a sec ago
//                    long timeOfPrevRequest = showSeatsRequestCollection.get(showSeatId);
//                    if (isDifferenceLessThanSecond(currentTime, timeOfPrevRequest)) {
//                        isPreviousRequestHasMoreSeats
//                    }
//
//
//                    return false;
//                }
//                //acquiring lock
//                isShowSeatLocked.put(showSeatId, 1);
//                showSeatsRequestCollection.put(showSeatId, currentTime);
//                return true;
//            }
//        }
//
//
//        showSeatsRequestCollection.add(showSeatList);
//        for (Long showSeatId : showSeatList) {
//            synchronized (showSeatId) {
//
//            }
//        }
//        //for all seats check if any of them has more than 1 request;
//        // if yes then check size of seats
        return false;
    }

    private static boolean isDifferenceLessThanSecond(long timestamp1, long timestamp2) {
        long difference = Math.abs(timestamp1 - timestamp2);
        return difference < 1000; // 1000 milliseconds
    }
}
//whenever a request came check if there are requests within 1 sec time span if there are then
// check if any of the request has overlapping seats
// if there are overlapping seats then give lock to request booking for more seats.

// case where 1st request gets the lock but second has more priority over 1st.

//userId - timestamp , List<ShowSeats>


