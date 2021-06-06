package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nrSeat;
    private Double price;
    private String firstNamePassenger;
    private String LastNamePassenger;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

}
