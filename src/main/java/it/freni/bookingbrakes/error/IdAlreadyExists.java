package it.freni.bookingbrakes.error;

import org.springframework.http.HttpStatus;

public class IdAlreadyExists extends RuntimeException {

        public IdAlreadyExists(String message) {
            super(message);
        }

        public HttpStatus getStatus() {
            return HttpStatus.BAD_REQUEST;
        }
    }

