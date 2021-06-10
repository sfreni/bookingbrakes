package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
public class CreditCard {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String numberCard;
    @Enumerated(EnumType.STRING)
    private IssuingNetwork issuingNetwork;
    private String firstName;
    private String lastName;
    private String dateExpiration;
    @OneToMany(mappedBy = "creditcard")
    private List<CreditCardTransaction> creditCardTransactions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;
}
