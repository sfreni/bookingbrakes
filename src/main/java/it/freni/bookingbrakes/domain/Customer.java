package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private Date birthDay;
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "customer")
    private List<CreditCard> creditCard;
}
