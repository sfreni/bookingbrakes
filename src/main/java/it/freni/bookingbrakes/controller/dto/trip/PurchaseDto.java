package it.freni.bookingbrakes.controller.dto.trip;

import it.freni.bookingbrakes.controller.dto.purchase.CustomerPurchaseDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseCreditCardTransactionDto;
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
    private CustomerPurchaseDto customer;

}
