package it.freni.bookingbrakes.controller.dto.purchase;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private Date birthDay;
}
