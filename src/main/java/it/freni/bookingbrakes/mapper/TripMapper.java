package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.trip.TripDto;
import it.freni.bookingbrakes.domain.Trip;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PurchaseMapper.class })
public abstract class TripMapper  {


    public abstract   TripDto toDto(Trip trip);

    public abstract Trip dtoToTrip(TripDto tripDto);

    public abstract  List<TripDto> toDtos(List<Trip> trips);

}
