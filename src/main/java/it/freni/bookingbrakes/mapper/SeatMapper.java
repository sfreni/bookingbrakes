package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatDto toDto(Seat seat);

  /*  @Mapping(target = "idTrip", source = "id")
    @Mapping(target = "id", ignore = true)
    SeatDtoOut tripToDtoOut(@MappingTarget SeatDtoOut dto, Trip trip);*/

    @Mapping(target = "id", source = "seat.id")
    @Mapping(target = "nrSeat", source = "seat.nrSeat")
    @Mapping(target = "startDateFlight", source = "trip.startDateFlight")
    @Mapping(target = "endDateFlight", source = "trip.endDateFlight")
    @Mapping(target = "airplane", source = "trip.airplane")
    @Mapping(target = "departure", source = "trip.departure")
    @Mapping(target = "destination", source = "trip.destination")
    @Mapping(target = "idTrip", source = "trip.id")
    SeatDtoOut seatAndTripToDto(Seat seat, Trip trip);

    List<SeatDto> toDtos(List<Seat> seats);

    Seat dtoToSeat(SeatDto seatDto);

    Seat dtoInToSeat(SeatDtoIn seatDtoIn);

  /*  CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtotoCustomer(CustomerDto customerDto);
    TripDto tripToTripDto(Trip trip);
    Trip tripDtoToTrip(TripDto tripDto);
*/
}
