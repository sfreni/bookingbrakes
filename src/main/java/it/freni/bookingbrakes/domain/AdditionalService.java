package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AdditionalService {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AdditionalServiceType additionalServiceType;
    private Double priceAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase")
    private Purchase purchase;

}
