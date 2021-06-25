package it.freni.bookingbrakes.controller.dto.creditcard;

import lombok.Data;

import java.util.List;

@Data
public class CreditCardDtoList {
    private CustomerDto customer;
    private List<CreditCardDtoSingle> creditCardDtoSingles;
}
