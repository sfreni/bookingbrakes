package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.controller.dto.AirplaneDto;
import it.freni.bookingbrakes.controller.dto.AirportDto;
import it.freni.bookingbrakes.controller.dto.BookingDto;
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
    private TripStatus tripStatus;

}
