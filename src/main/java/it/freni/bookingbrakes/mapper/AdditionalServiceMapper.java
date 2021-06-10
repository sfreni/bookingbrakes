package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.AdditionalServiceDto;
import it.freni.bookingbrakes.domain.AdditionalService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdditionalServiceMapper {
    AdditionalServiceDto toDto(AdditionalService additionalService);

    List<AdditionalServiceDto> toDtos(List<AdditionalService> additionalServices);

    AdditionalService dtoToAdditionalService(AdditionalServiceDto additionalServiceDto);


  /*  CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtotoCustomer(CustomerDto customerDto);
    TripDto tripToTripDto(Trip trip);
    Trip tripDtoToTrip(TripDto tripDto);
*/
}
