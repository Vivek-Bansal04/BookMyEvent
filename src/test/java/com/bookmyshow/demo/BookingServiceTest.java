package com.bookmyshow.demo;

import com.bookmyshow.api.models.ShowSeat;
import com.bookmyshow.api.services.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testPessimisticLockingFailureException() throws Exception {
        // Assume we have a show with ID 1 and seats with IDs 1, 2, 3
        Long showId = 1L;
        List<Long> showSeatIds = Arrays.asList(1L, 2L, 3L);
        Long userId = 1L;

        // Lock a seat manually to simulate a conflict
        entityManager.getTransaction().begin();
        ShowSeat seatToLock = entityManager.find(ShowSeat.class, 1L, LockModeType.PESSIMISTIC_WRITE);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Thread 1: Simulate booking in a separate thread
        Future<?> future1 = executorService.submit(() -> {
            try {
                bookingService.bookTickets(showId, showSeatIds, userId);
            } catch (Exception e) {
                // Handle exception (expected due to locking conflict)
            }
        });

        // Thread 2: Simulate booking in another separate thread
        Future<?> future2 = executorService.submit(() -> {
            try {
                bookingService.bookTickets(showId, showSeatIds, userId);
            } catch (Exception e) {
                // Handle exception (expected due to locking conflict)
            }
        });

        future1.get();
        future2.get();

        // Clean up
        entityManager.getTransaction().commit();
        entityManager.clear();

        executorService.shutdown();
    }
}
