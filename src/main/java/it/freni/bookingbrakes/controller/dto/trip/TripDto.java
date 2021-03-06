package it.freni.bookingbrakes.controller.dto.trip;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.controller.dto.airport.AirportDto;
import it.freni.bookingbrakes.domain.TripStatus;
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
        private List<PurchaseWithoutTripDto> purchases;
    private TripStatus tripStatus;
}
