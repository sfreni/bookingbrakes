package it.freni.bookingbrakes.controller.dto;

import lombok.Data;

@Data
public class AirportDto {
    private Long id;
    private String name;
    private String shortName;
    private String country;
    private String city;
    private String streetAddress;
    private String postalCode;
    private String stateProvince;
}
