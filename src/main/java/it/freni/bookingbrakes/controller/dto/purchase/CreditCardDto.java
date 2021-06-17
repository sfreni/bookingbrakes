package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
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
