package it.freni.bookingbrakes.error;

import org.springframework.http.HttpStatus;

public class NotObjectFound extends RuntimeException {

    public NotObjectFound(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
