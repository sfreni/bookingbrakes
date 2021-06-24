package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import lombok.Data;

import java.util.Date;

@Data
public class SeatDtoOut {
        private Long idTrip;
        private Date startDateFlight ;
        private Date endDateFlight ;
        private AirplaneDto airplane;
        private AirportDto departure;
        private AirportDto destination;
        private Long id;
        private String nrSeat;
        private CustomerDto customer;
}
