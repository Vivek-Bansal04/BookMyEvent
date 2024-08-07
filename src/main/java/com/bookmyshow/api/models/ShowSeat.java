package com.bookmyshow.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_show_id",referencedColumnName = "id",nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_seat_id",referencedColumnName = "id",nullable = false)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private ShowSeatState state;
}
