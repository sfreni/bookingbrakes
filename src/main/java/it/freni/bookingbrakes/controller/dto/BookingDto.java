package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDto {
    private Long id;
    private CustomerDto customer;
    private Date dateBooking;
    private String creditCard;
    private List<SeatDto> seats;
    private TripDto trip;
    private BookingStatus bookingStatus;
}
