package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import lombok.Data;

@Data
public class CreditCardTransactionSeatDto {
        private Long id;
        private Double totalePriceAmount;
        private CreditCardTransactionStatus transactionStatus;
        private CreditCardSeatDto creditcard;
}
