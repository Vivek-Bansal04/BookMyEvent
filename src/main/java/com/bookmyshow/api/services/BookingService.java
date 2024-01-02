package com.bookmyshow.api.services;

import com.bookmyshow.api.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.api.models.*;
import com.bookmyshow.api.repositories.ShowRepository;
import com.bookmyshow.api.repositories.ShowSeatRepository;
import com.bookmyshow.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(
            Long showId,
            List<Long> showSeatIds,
            Long userId
    ) throws ShowSeatNotAvailableException {
        // Fetch given ShowSeats
        List<ShowSeat> showSeats = showSeatRepository.findByIdIn(showSeatIds);

        // Check if each of them are available
        for (ShowSeat showSeat: showSeats) {
            if (showSeat.getState() != ShowSeatState.AVAILABLE) {
                throw new ShowSeatNotAvailableException("ShowSeat ID: " +
                        showSeat.getId() + " not available.");
            }
        }

        // Update status to locked
        for (ShowSeat showSeat: showSeats) {
            showSeat.setState(ShowSeatState.LOCKED);
            showSeatRepository.save(showSeat);
        }
        Show show = showRepository.findById(showId).get();

        // Return the booking object
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
}
