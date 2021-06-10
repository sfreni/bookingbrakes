package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CreditCardTransaction {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "creditcard_id")
        private CreditCard creditcard;
        private Double totalePriceAmount;
        @Enumerated(EnumType.STRING)
        private CreditCardTransactionStatus transactionStatus;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "purchase")
        private Purchase purchase;

}
