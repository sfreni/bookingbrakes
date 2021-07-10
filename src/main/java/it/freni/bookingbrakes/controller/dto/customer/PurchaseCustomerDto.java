package it.freni.bookingbrakes.controller.dto.customer;

import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.TripDto;
import it.freni.bookingbrakes.domain.PurchaseStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseCustomerDto {
    private Long id;
    private List<ProductDto> products;
    private Date datePurchase;
    private PurchaseStatus purchaseStatus;
    private TripDto trip;

}
