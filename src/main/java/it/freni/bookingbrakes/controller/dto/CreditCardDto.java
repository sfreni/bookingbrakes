package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.controller.dto.CreditCardTransaction.CreditCardTransactionDto;
import lombok.Data;

import java.util.List;

@Data
public class CreditCardDto {
    private Long id;
    private String numberCard;
    private String issuingNetwork;
    private String firstName;
    private String lastName;
    private String dateExpiration;
    private CustomerWithIdDto customer;
    private List<CreditCardTransactionDto> creditCardTransactions;
}
