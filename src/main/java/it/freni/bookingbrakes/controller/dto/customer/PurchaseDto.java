package it.freni.bookingbrakes.controller.dto.customer;

import it.freni.bookingbrakes.controller.dto.purchase.TripDto;
import it.freni.bookingbrakes.domain.PurchaseStatus;
import lombok.Data;

import java.util.Date;

@Data
public class PurchaseDto {
    private Long id;

    private Date datePurchase;
    private PurchaseStatus purchaseStatus;
    private TripDto trip;

}
