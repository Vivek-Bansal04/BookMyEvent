package com.bookmyshow.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreDTO {
    String name;
    String address;
    Long cityId;
}
