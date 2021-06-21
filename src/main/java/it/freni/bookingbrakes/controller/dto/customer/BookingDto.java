package it.freni.bookingbrakes.controller.dto.customer;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.controller.dto.purchase.TripDto;
import it.freni.bookingbrakes.domain.BookingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDto {
    private Long id;
    private CustomerDto customer;
    private Date dateBooking;
    private TripDto trip;
    private BookingStatus bookingStatus;
}
