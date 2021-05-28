package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.StatusTrip;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripDto {
    private Long id;
    private Date startDateFlight ;
    private Date endDateFlight ;
    private AirplaneDto airplane;
    private AirportDto departure;
    private AirportDto destination;
    private List<SeatDto> seats;
    private StatusTrip statusTrip;
}
