package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import lombok.Data;

@Data
public class CreditCardTransactionDto {
        private Long id;
        private Double totalePriceAmount;
        private CreditCardTransactionStatus transactionStatus;
        private CreditCardNoTransactionsDto creditcard;
        private PurchaseDto purchase;
}
