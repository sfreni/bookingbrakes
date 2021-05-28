package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.TypeAirplanes;
import lombok.Data;

@Data
public class AirplaneDto {
    private Long id;
    private TypeAirplanes typeAirplanes;
    private String name;
    private Integer numberSeats;
    private  Boolean avaibleFlight;
}
