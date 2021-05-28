package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
@Data
@Entity
public class Airplane {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeAirplanes typeAirplanes;
    private String name;
    private Integer numberSeats;
    private  Boolean avaibleFlight;
}
