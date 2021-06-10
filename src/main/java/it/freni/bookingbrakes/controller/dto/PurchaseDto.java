package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.*;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class PurchaseDto {
    private Long id;
    private List<Seat> seats;
    private List<AdditionalService> additionalServices;
    private List<CreditCardTransaction> creditCardTransactions;
    private PurchaseStatus purchaseStatus;
    private Booking booking;
}
