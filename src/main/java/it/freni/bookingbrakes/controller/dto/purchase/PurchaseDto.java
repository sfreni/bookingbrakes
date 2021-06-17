package it.freni.bookingbrakes.controller.dto.purchase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.freni.bookingbrakes.domain.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class PurchaseDto {
    private Long id;
    private List<ProductDto> products;
    private List<PurchaseCreditCardTransactionDto> creditCardTransactions;
    private Date datePurchase;
    private PurchaseStatus purchaseStatus;
    private BookingDto booking;
}
