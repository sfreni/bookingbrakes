package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.BookingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingTransactionDto {
    private Long id;
    private Date dateBooking;
    private TripTransactionDto trip;
    private BookingStatus bookingStatus;
}
