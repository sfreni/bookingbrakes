package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);
    Iterable<CustomerDto> toDtos(Iterable<Customer> customers);

    Customer dtoToCustomer(CustomerDto customerDto);

}
