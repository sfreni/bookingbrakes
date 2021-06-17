package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("seats")
@Data
public class Seat extends Product{
   private String nrSeat;
    private String firstNamePassenger;
    private String lastNamePassenger;
    private Date dateOfBirth;
    private Long id;
}
