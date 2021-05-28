package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.AirportDto;
import it.freni.bookingbrakes.domain.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
        AirportDto toDto(Airport airport);
        Iterable<AirportDto> toDtos(Iterable<Airport> airports);
        Airport dtoToAirport(AirportDto airportDto);
}
