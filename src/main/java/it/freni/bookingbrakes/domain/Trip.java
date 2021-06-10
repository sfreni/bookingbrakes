package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    private Date startDateFlight ;
    private Date endDateFlight ;
    @OneToOne
    private Airplane airplane;
    @OneToOne
    private Airport departure;
    @OneToOne
    private Airport destination;
    @OneToMany(mappedBy = "trip")
    List<Booking> bookings ;


}
