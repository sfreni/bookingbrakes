package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.controller.dto.SeatDto;
import it.freni.bookingbrakes.controller.dto.SeatDtoOut;
import it.freni.bookingbrakes.controller.dto.TripDto;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatDto toDto(Seat seat);
    SeatDtoOut toDtoOut(Seat seat);
    @Mapping(target = "idTrip", source = "id")
    @Mapping(target = "id", ignore = true)
    SeatDtoOut tripToDtoOut(@MappingTarget SeatDtoOut dto, Trip trip);

    Iterable<SeatDto> toDtos(Iterable<Seat> seats);
    Seat dtoToSeat(SeatDto seatDto);

    CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtotoCustomer(CustomerDto customerDto);
    TripDto tripToTripDto(Trip trip);
    Trip tripDtoToTrip(TripDto tripDto);

}
