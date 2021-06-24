package it.freni.bookingbrakes.controller.dto;

import it.freni.bookingbrakes.domain.AirplaneType;
import lombok.Data;

@Data
public class AirplaneDto {
    private Long id;
    private AirplaneType airplaneType;
    private String name;
    private Integer numberSeats;
    private  Boolean avaibleFlight;
}
