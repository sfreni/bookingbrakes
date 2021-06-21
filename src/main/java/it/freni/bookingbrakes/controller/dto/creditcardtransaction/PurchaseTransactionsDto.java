package it.freni.bookingbrakes.controller.dto.creditcardtransaction;

import it.freni.bookingbrakes.controller.dto.purchase.BookingDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.domain.PurchaseStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseTransactionsDto {
    private Long id;
    private List<ProductDto> products;
    private Date datePurchase;
    private PurchaseStatus purchaseStatus;
    private BookingDto booking;
}
