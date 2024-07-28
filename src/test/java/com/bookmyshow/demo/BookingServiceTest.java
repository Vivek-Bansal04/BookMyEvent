package com.bookmyshow.demo;

import com.bookmyshow.api.BookMyShowApplication;
import com.bookmyshow.api.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.api.models.Booking;
import com.bookmyshow.api.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookMyShowApplication.class)
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    private List<Long> showSeatIds1;
    private List<Long> showSeatIds2;
    private Long showId;
    private Long userId1;
    private Long userId2;

    @BeforeEach
    public void setUp() {
        showSeatIds1 = Arrays.asList(4L, 5L, 6L,7L,8L);
        showSeatIds2 = Arrays.asList(6L, 7L,8L,9L);
        showId = 6L;
        userId1 = 1L;
        userId2 = 3L;
    }

    @Test
    public void testConcurrentBooking() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Booking> future1 = executor.submit(() -> {
            try {
                return bookingService.bookTickets(showId, showSeatIds1, userId1);
            } catch (ShowSeatNotAvailableException e) {
                throw new RuntimeException(e);
            }
        });

        Future<Booking> future2 = executor.submit(() -> {
            try {
                return bookingService.bookTickets(showId, showSeatIds2, userId2);
            } catch (ShowSeatNotAvailableException e) {
                throw new RuntimeException(e);
            }
        });

        boolean oneFailed = false;
        try {
            future1.get();
        } catch (Exception e) {
            oneFailed = true;
            System.out.println("failedd future 1");
        }

        try {
            future2.get();
        } catch (Exception e) {
            oneFailed = true;
            System.out.println("failedd future 2");
        }

        assertTrue(oneFailed, "One of the bookings should fail due to seat unavailability");

        executor.shutdown();
    }
}
