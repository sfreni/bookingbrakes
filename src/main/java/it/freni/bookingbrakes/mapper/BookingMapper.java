package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.Booking;
import it.freni.bookingbrakes.domain.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target="trip.bookings", ignore = true)
    BookingDto bookingToBookingDto(Booking booking);
    Iterable<BookingDto> listToListDtos(Iterable<Booking> bookingsList);
    Booking bookingDtoInToBooking(BookingDtoIn bookingDtoIn);

    @Mapping(target = "id", source = "booking.id")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "dateBooking", source = "booking.dateBooking")
    @Mapping(target = "creditCard", source = "booking.creditCard")
    @Mapping(target = "startDateFlight", source = "trip.startDateFlight")
    @Mapping(target = "endDateFlight", source = "trip.endDateFlight")
    @Mapping(target = "airplane", source = "trip.airplane")
    @Mapping(target = "departure", source = "trip.departure")
    @Mapping(target = "destination", source = "trip.destination")
    @Mapping(target = "idTrip", source = "trip.id")
    @Mapping(target = "seats", source = "seatsDto")
    @Mapping(target = "bookingStatus", source = "booking.bookingStatus")

    BookingDtoOut bookingAndTripToDto(Booking booking, Trip trip, List<SeatDto> seatsDto, CustomerDto customer);

}
