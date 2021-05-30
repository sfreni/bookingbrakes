package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Trip {
@Id
    private Long id;
    private Date startDateFlight ;
    private Date endDateFlight ;
    @OneToOne
    private Airplane airplane;
    @OneToOne
    private Airport departure;
    @OneToOne
    private Airport destination;
    @OneToMany(mappedBy = "trip")
    List<Seat> seats;
    @Enumerated(EnumType.STRING)
    private StatusTrip statusTrip;
}
