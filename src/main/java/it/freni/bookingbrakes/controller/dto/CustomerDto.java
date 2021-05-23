package it.freni.bookingbrakes.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    private Long id;
    private String nameFirstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private Date birthDay;
}
