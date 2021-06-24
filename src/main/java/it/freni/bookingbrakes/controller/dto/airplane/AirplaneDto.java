package it.freni.bookingbrakes.controller.dto.airplane;

import it.freni.bookingbrakes.domain.AirplaneType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class AirplaneDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private AirplaneType airplaneType;
    private String name;
    private Integer numberSeats;
    private  Boolean avaibleFlight;
}
