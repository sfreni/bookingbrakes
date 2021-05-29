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
    @OneToMany
    @JoinColumn(name="Trip_Id")
    List<Seat> seats;
    @Enumerated(EnumType.STRING)
    private StatusTrip statusTrip;
}
