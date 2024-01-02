package com.bookmyshow.api.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBookingRequestDTO {
    Long showId;
    List<Long> showSeatIds;
    Long userId;
}
