package com.bookmyshow.api.dtos;

import com.bookmyshow.api.models.SeatType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AuditoriumDTO {
    String name;
    int capacity;
    private Map<SeatType, Integer> seatCount;
}
