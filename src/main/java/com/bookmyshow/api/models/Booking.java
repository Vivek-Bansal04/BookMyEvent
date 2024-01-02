package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
//Booking
public class Booking extends BaseModel {
    @ManyToOne
    private User bookedBy;

    @ManyToOne
    private Show show;

    // 1 : M
    // 1  : 1  // if no cancellation -> @OneToMany
    @ManyToMany
    private List<ShowSeat> showSeats;
    private double totalAmount;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private Date timeOfBooking;
}