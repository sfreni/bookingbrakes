package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.AdditionalServiceType;
import it.freni.bookingbrakes.domain.Purchase;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class AdditionalServiceDto {

    private Long id;
    private AdditionalServiceType additionalServiceType;
    private Double priceAmount;
    private PurchaseSeatDto purchase;
}
