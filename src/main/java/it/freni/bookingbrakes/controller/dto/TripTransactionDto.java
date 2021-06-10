package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.TripStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TripTransactionDto {
    private Long id;
    private Date startDateFlight ;
    private Date endDateFlight ;
    private AirplaneDto airplane;
    private AirportDto departure;
    private AirportDto destination;
     private TripStatus tripStatus;
}
