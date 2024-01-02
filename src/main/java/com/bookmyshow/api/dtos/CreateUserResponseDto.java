package com.bookmyshow.api.dtos;

import com.bookmyshow.api.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponseDto {
    private User user;
}
