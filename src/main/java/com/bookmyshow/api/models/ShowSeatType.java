package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "show_seattype_mapping")
//used for getting price of a particular seatType for a particular show
public class ShowSeatType extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "fk_show_id", referencedColumnName = "id",nullable = false)
    private Show show;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private double price;
}
