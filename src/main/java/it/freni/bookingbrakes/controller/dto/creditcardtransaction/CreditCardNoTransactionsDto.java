package it.freni.bookingbrakes.controller.dto.creditcardtransaction;

import it.freni.bookingbrakes.controller.dto.creditcard.CustomerDto;
import lombok.Data;

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
