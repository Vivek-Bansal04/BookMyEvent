package com.bookmyshow.api.services;

import com.bookmyshow.api.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.api.models.*;
import com.bookmyshow.api.repositories.ShowRepository;
import com.bookmyshow.api.repositories.ShowSeatRepository;
import com.bookmyshow.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {
    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;

    @Autowired
    public BookingService(
            ShowSeatRepository showSeatRepository,
            UserRepository userRepository,
            ShowRepository showRepository
    ) {
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    ConcurrentHashMap<Long,Integer> isShowSeatLocked = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long,String> showSeatLockOwner = new ConcurrentHashMap<>();

    //implementation using pessimistic lock by spring
//    @Transactional(isolation = Isolation.SERIALIZABLE)
//    public Booking bookTicket(
//            Long showId,
//            List<Long> showSeatIds,
//            Long userId
//    ) throws ShowSeatNotAvailableException {
//        // Fetch given ShowSeats
//        List<ShowSeat> showSeats = showSeatRepository.findByIdIn(showSeatIds);
//
//        // Check if each of them are available
//        for (ShowSeat showSeat: showSeats) {
//            if (showSeat.getState() != ShowSeatState.AVAILABLE) {
//                throw new ShowSeatNotAvailableException("ShowSeat ID: " +
//                        showSeat.getId() + " not available.");
//            }
//        }
//
//        // Update status to locked
//        for (ShowSeat showSeat: showSeats) {
//            showSeat.setState(ShowSeatState.LOCKED);
//            showSeatRepository.save(showSeat);
//        }
//        Show show = showRepository.findById(showId).get();
//        // Return the booking object
//        return getBooking(userId, showSeats, show);
//    }

// implementation using custom lock
    public Booking bookTicket(
            Long showId,
            List<Long> showSeatIds,
            Long userId
    ) throws ShowSeatNotAvailableException {

        // to avoid deadlock sorting showSeats explained in Readme
        Collections.sort(showSeatIds);
        int count = 0;
        for(Long showSeat : showSeatIds){
            boolean isLockAcquired = getLockOverShowSeat(showSeat);
            if(!isLockAcquired){
                //when unable to acquire all seats
                releaseAllSeats(showSeatIds,count);
                throw new ShowSeatNotAvailableException("ShowSeat ID: " +
                        showSeat + " not available.");
            }
            count++;
        }

        // Fetch given ShowSeats
        List<ShowSeat> showSeats = showSeatRepository.findByIdIn(showSeatIds);

        // Check if each of them are available
        for (ShowSeat showSeat: showSeats) {
            if (showSeat.getState() != ShowSeatState.AVAILABLE) {
                releaseAllSeats(showSeatIds,showSeatIds.size());
                throw new ShowSeatNotAvailableException("All seats are not available");
            }
        }

        // Update status to locked
        for (ShowSeat showSeat: showSeats) {
            showSeat.setState(ShowSeatState.LOCKED);
            showSeatRepository.save(showSeat);
        }
        Show show = showRepository.findById(showId).get();
        // Return the booking object
        Booking booking =  getBooking(userId, showSeats, show);
        releaseAllSeats(showSeatIds,showSeatIds.size());
        return booking;
    }

    private Booking getBooking(Long userId, List<ShowSeat> showSeats, Show show) {
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setBookedBy(userRepository.findById(userId).get());
        booking.setShow(show);
        booking.setShowSeats(showSeats);
        booking.setTimeOfBooking(new Date());
        int totalAmount = 0;
        for(ShowSeat showSeat : showSeats){
            for(ShowSeatType showSeatType: show.getShowSeatTypes()){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    totalAmount+=showSeatType.getPrice();
                }
            }
        }
        booking.setTotalAmount(totalAmount);
        return booking;
    }

    public boolean getLockOverShowSeat(Long showSeatId){
        //added synchronized here if 2 threads try to access this at same time can cause issues
        synchronized (showSeatId){
            if(isShowSeatLocked.containsKey(showSeatId) && isShowSeatLocked.get(showSeatId).equals(1)){
                //means it is already locked
                return false;
            }
            //acquiring lock
            showSeatLockOwner.put(showSeatId,Thread.currentThread().getName());
            isShowSeatLocked.put(showSeatId,1);
            return true;
        }
    }

    //TODO we can check if same thread then only unlock as can lead to issues if some other thread unlocks it
    public boolean unlockShowSeat(Long showSeatId){
        if(showSeatLockOwner.get(showSeatId).equals(Thread.currentThread().getName())){
            isShowSeatLocked.put(showSeatId, 0);
            return true;
        }
        return false;
    }

    public void releaseAllSeats(List<Long> showSeatIds, int count){
        for(int i=0;i< count;++i){
            unlockShowSeat(showSeatIds.get(i));
        }
    }

}
