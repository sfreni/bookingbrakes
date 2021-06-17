package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import lombok.Data;

@Data
public class PurchaseCreditCardTransactionDto {
        private Long id;
        private CreditCardDto creditcard;
        private Double totalePriceAmount;
        private CreditCardTransactionStatus transactionStatus;
        private PurchaseDto purchaseDto;
}
