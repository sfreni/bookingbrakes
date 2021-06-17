package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("additionalservices")
public class AdditionalService extends Product{
    @Enumerated(EnumType.STRING)
    private AdditionalServiceType additionalServiceType;


}
