package it.freni.bookingbrakes.controller.dto;

import lombok.Data;

@Data
public class CreditCardSeatDto {
    private Long id;
    private String numberCard;
    private String issuingNetwork;
    private String firstName;
    private String lastName;
    private String dateExpiration;
}
