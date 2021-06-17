package it.freni.bookingbrakes.controller.dto.purchase;

import it.freni.bookingbrakes.domain.AdditionalServiceType;
import lombok.Data;

@Data
public class ProductAdditionalServiceDto extends ProductDto {
    private AdditionalServiceType additionalServiceType;
}
