package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.AirplaneDto;
import it.freni.bookingbrakes.controller.dto.AirportDto;
import it.freni.bookingbrakes.controller.dto.SeatDto;
import it.freni.bookingbrakes.controller.dto.TripDto;
import it.freni.bookingbrakes.domain.Airplane;
import it.freni.bookingbrakes.domain.Airport;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper  {
    TripDto toDto(Trip trip);
    Trip dtoToTrip(TripDto tripDto);

    Iterable<TripDto> toDtos(Iterable<Trip> trips);

    Airplane airplaneDtoToAirplane(AirplaneDto airplaneDto);
    AirplaneDto airplaneToDto(Airplane airplane);

    Airport airportDtoToAirport(AirportDto airportDto);
    AirportDto airportToAirportDto(Airport airport);

    List<SeatDto> seatToSeatDto(List<Seat> seats);
    List<Seat> seatDtoToSeat(List<SeatDto> seatDtos);


}
