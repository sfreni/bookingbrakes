package it.freni.bookingbrakes.controller.dto.creditcardtransaction;

import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import lombok.Data;

@Data
public class CreditCardTransactionDto {
        private Long id;
        private Double totalePriceAmount;
        private CreditCardTransactionStatus transactionStatus;
        private CreditCardNoTransactionsDto creditcard;
        private PurchaseTransactionsDto purchase;
}
