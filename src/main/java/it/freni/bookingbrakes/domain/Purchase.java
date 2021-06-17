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

    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

}
