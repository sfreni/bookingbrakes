package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Seat {
    @Id
    private Long id;
    private String nrSeat;
    @OneToOne
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
