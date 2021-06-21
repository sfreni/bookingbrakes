package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.controller.dto.creditcard.CreditCardDto;
import it.freni.bookingbrakes.domain.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDtoOut {
        private Long id;
        private Long idTrip;
        private Date startDateFlight ;
        private Date endDateFlight ;
        private AirplaneDto airplane;
        private AirportDto departure;
        private AirportDto destination;
        private CustomerDto customer;
        private Date dateBooking;
        private List<SeatDto> seats ;
        private BookingStatus bookingStatus;
        private List<CreditCardDto> creditCards;
}
