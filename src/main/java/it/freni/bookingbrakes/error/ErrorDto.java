package it.freni.bookingbrakes.error;

import lombok.Data;

@Data
public class ErrorDto {
        private String status;
        private String message;
        private String time;
}
