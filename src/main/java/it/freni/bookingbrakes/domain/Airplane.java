package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AirplaneType airplaneType;
    private String name;
    private Integer numberSeats;
    private  Boolean avaibleFlight;
}
