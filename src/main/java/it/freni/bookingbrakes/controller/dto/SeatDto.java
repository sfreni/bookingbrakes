package it.freni.bookingbrakes.controller.dto;

import lombok.Data;


import java.util.Date;

@Data
public class SeatDto {
    private Long id;
    private String nrSeat;
    private Double priceAmount;
    private String firstNamePassenger;
    private String lastNamePassenger;
    private Date dateOfBirth;
    private PurchaseSeatDto purchase;
}
