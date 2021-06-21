package it.freni.bookingbrakes.controller.dto.customer;

import lombok.Data;

@Data
public class CreditCardDto {
    private Long id;
    private String numberCard;
    private String issuingNetwork;
    private String firstName;
    private String lastName;
    private String dateExpiration;
}
