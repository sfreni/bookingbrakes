package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingTripDto {
    private Long id;
    private CustomerDto customer;
    private Double price;
    private Date dateBooking;
    private List<SeatDto> seats;
     private BookingStatus bookingStatus;
}
