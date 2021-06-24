package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.controller.dto.airport.AirportDto;
import it.freni.bookingbrakes.domain.TripStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TripDto {
    private Long id;
    private Date startDateFlight ;
    private Date endDateFlight ;
    private AirplaneDto airplane;
    private AirportDto departure;
    private AirportDto destination;
    private TripStatus tripStatus;
}
