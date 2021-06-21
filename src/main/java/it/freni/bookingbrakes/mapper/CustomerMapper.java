package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.controller.dto.CustomerWithIdDto;
import it.freni.bookingbrakes.controller.dto.customer.CustomerControllerDto;
import it.freni.bookingbrakes.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);
    Iterable<CustomerDto> toDtos(Iterable<Customer> customers);
    Customer dtoToCustomer(CustomerDto customerDto);
    CustomerWithIdDto toDtoWithId(Customer customer);

    CustomerControllerDto toCustomerControllerDto(Customer customer);
    Customer customerControllerDtoToCustomer(CustomerControllerDto customer);
    Iterable<CustomerControllerDto> toCustomerControllerDtos(Iterable<Customer> customers);


}
