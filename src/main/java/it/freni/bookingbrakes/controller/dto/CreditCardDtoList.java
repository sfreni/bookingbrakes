package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Customer;
import lombok.Data;

import java.util.List;

@Data
public class CreditCardDtoList {
    private CustomerDto customerCreditCardDto;
    private List<CreditCardDtoSingle> creditCardDtoList;
}
