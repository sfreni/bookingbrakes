package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.domain.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

    List<CustomerDto> toDtos(List<Customer> customers);

}
