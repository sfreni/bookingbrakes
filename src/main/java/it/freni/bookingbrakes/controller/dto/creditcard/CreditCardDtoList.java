package it.freni.bookingbrakes.controller.dto.creditcard;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import lombok.Data;

import java.util.List;

@Data
public class CreditCardDtoList {
    private CustomerDto customer;
    private List<CreditCardDtoSingle> creditCardDtoList;
}
