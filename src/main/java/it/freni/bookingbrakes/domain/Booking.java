package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;
    private Date dateBooking;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @OneToMany(mappedBy = "booking")
    List<Purchase> purchases;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}