package it.freni.bookingbrakes.controller.dto.creditcard;

import it.freni.bookingbrakes.domain.PurchaseStatus;
import lombok.Data;

import java.util.Date;

@Data
public class PurchaseTransactionsDto {
    private Long id;
    private Date datePurchase;
    private PurchaseStatus purchaseStatus;
    private TripDto trip;
}
