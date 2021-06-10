package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nrSeat;
    private Double priceAmount;
    private String firstNamePassenger;
    private String lastNamePassenger;
    private Date dateOfBirth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase")
    private Purchase purchase;

}
