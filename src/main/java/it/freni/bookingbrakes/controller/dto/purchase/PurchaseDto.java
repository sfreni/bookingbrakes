package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.domain.PurchaseStatus;
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
    private TripDto trip;
    private CustomerPurchaseDto customer;

}
