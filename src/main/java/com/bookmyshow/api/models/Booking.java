package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Booking extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User bookedBy;

    @ManyToOne
    @JoinColumn(name = "fk_show_id")
    private Show show;

    // 1 : M
    // 1  : 1  // if no cancellation -> @OneToMany,
    // currently many to many as we will give support for cancellation of booking
    @ManyToMany
    private List<ShowSeat> showSeats;
    private double totalAmount;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private LocalDateTime timeOfBooking;
}