package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.controller.dto.BookingSeatDto;
import it.freni.bookingbrakes.controller.dto.CreditCardTransactionSeatDto;
import it.freni.bookingbrakes.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseSeatDto {
    private Long id;
    private List<CreditCardTransactionSeatDto> creditCardTransactions;
    private PurchaseStatus purchaseStatus;
    private BookingSeatDto booking;
}
