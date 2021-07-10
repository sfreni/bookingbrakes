package it.freni.bookingbrakes.controller.dto.customer;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerControllerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private Date birthDay;
    private List<PurchaseCustomerDto> purchases;
    private List<CreditCardDto> creditCard;

}
