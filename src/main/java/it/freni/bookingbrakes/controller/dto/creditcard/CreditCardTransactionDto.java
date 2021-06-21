package it.freni.bookingbrakes.controller.dto.creditcard;

import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import lombok.Data;

@Data
public class CreditCardTransactionDto {
        private Long id;
        private Double totalePriceAmount;
        private CreditCardTransactionStatus transactionStatus;
        private PurchaseTransactionsDto purchase;
}
