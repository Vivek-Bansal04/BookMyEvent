package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class Seat extends BaseModel {
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;
}
