package it.freni.bookingbrakes.controller.dto.purchase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.freni.bookingbrakes.domain.Purchase;
import lombok.Data;

import javax.persistence.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProductSeatDto.class, name = "Seat"),
        @JsonSubTypes.Type(value = ProductAdditionalServiceDto.class, name = "AdditionalService")
})@Data
public abstract class ProductDto {
    private Long id;
    private Double priceAmount;
}
