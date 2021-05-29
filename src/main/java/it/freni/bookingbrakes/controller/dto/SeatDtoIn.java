package it.freni.bookingbrakes.controller.dto;

import lombok.Data;

@Data
public class SeatDtoIn {
    private Long id;
    private String nrSeat;
    private CustomerDto customer;
    private TripDto trip;
}
