package it.freni.bookingbrakes.controller.dto.purchase;

import lombok.Data;

import java.util.Date;

@Data
public class ProductSeatDto extends ProductDto {
    private String nrSeat;
    private String firstNamePassenger;
    private String lastNamePassenger;
    private Date dateOfBirth;
}
