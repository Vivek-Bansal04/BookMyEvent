package com.bookmyshow.api.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InternalServerException() {
        super("Something went wrong, please try again!");
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String resource, String method, String message) {
        super(String.format("%s -> %s : '%s'", resource, method, message));
    }
}
