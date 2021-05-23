package it.freni.bookingbrakes.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Customer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private Date birthDay;
}
