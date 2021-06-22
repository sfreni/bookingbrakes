package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Purchase  {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "purchase")
    private List<Product> products;
     @OneToMany(mappedBy = "purchase")
    private List<CreditCardTransaction> creditCardTransactions;
    private Date datePurchase;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

}
