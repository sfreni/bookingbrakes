package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseSeatDto {
    private Long id;
    private List<CreditCardTransactionSeatDto> creditCardTransactions;
    private PurchaseStatus purchaseStatus;
    private TripDto tripDto;
}
