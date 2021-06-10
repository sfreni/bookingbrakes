package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.CreditCardTransaction;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
