package com.bookmyshow.api.dtos;

import com.bookmyshow.api.models.Language;
import com.bookmyshow.api.models.SeatType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ShowDTO {
//    @NotBlank
    Long movieId;
    LocalDate startTime;
    LocalDate endTime;
    Long auditoriumId;
    Map<SeatType, Integer> seatPricing;
    Language language;
}
