package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.BookingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingSeatDto {
    private Long id;
    private CustomerDto customer;
    private Date dateBooking;
    private TripSeatDto trip;
    private BookingStatus bookingStatus;
}
