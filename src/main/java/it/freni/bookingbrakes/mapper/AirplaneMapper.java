package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.AirplaneDto;
import it.freni.bookingbrakes.domain.Airplane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirplaneMapper {

    AirplaneDto toDto(Airplane airplane);

    Iterable<AirplaneDto> toDtos(Iterable<Airplane> airplanes);

    Airplane dtoToAirplane(AirplaneDto airplaneDto);

}
