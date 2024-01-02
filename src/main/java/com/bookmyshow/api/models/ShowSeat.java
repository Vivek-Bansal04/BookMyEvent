package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "show_seat_mapping")
//this depicts every seat in a show I could have added price in this but price is fixed for every seatType so avoid it in Db
// created a separate class ShowSeatType for pricing of seat
public class ShowSeat extends BaseModel {
    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private ShowSeatState state;
}
