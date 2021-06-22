package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.controller.dto.trip.TripDto;
import it.freni.bookingbrakes.controller.dto.trip.TripDtoOut;
import it.freni.bookingbrakes.domain.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper  {
    TripDto toDto(Trip trip);
    Trip dtoToTrip(TripDto tripDto);
    TripDtoOut toDtoOut(Trip Trip);
    Iterable<TripDto> toDtos(Iterable<Trip> trips);

    Airplane airplaneDtoToAirplane(AirplaneDto airplaneDto);
    AirplaneDto airplaneToDto(Airplane airplane);

    Airport airportDtoToAirport(AirportDto airportDto);
    AirportDto airportToAirportDto(Airport airport);

    List<SeatDto> seatToSeatDto(List<Seat> seats);
    List<Seat> seatDtoToSeat(List<SeatDto> seatDtos);


}
