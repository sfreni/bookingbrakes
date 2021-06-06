package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.BookingStatus;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDtoIn {
    private Long id;
    private CustomerDto customer;
    private Date dateBooking;
    private String creditCard;

    private Trip trip;
    private List<Seat> seats ;
    private BookingStatus bookingStatus;
}
