package com.bookmyshow.api.dtos;

import com.bookmyshow.api.models.Language;
import com.bookmyshow.api.models.SeatType;
import com.bookmyshow.api.models.ShowFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowDTO {
//    @NotBlank
    Long movieId;

    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;

    Long auditoriumId;
    Map<SeatType, Integer> seatPricing;
    Language language;

    List<ShowFeature> showFeatures;
}
