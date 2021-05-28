package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Airport {
@Id
private Long id;
private String name;
private String shortName;
private String country;
private String city;
private String streetAddress;
private String postalCode;
private String stateProvince;
}
