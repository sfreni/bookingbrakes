package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.TripStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripDtoOut {
    private Long id;
    private Date startDateFlight ;
    private Date endDateFlight ;
    private AirplaneDto airplane;
    private AirportDto departure;
    private AirportDto destination;
    private List<BookingTripDto> bookings;
    private TripStatus tripStatus;
}
