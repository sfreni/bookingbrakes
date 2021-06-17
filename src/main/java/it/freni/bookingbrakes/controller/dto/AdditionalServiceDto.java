package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.controller.dto.purchase.PurchaseSeatDto;
import it.freni.bookingbrakes.domain.AdditionalServiceType;
import lombok.Data;

@Data
public class AdditionalServiceDto {

    private Long id;
    private AdditionalServiceType additionalServiceType;
    private Double priceAmount;
    private PurchaseSeatDto purchase;
}
