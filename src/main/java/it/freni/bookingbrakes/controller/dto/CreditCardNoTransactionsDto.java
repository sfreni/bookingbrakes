package it.freni.bookingbrakes.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreditCardNoTransactionsDto {
    private Long id;
    private String numberCard;
    private String issuingNetwork;
    private String firstName;
    private String lastName;
    private String dateExpiration;
    private CustomerDto customerDto;
}
