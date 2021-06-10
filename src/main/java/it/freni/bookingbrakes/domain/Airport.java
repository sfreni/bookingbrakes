package it.freni.bookingbrakes.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Airport {
@Id
@GeneratedValue(strategy= GenerationType.AUTO)
private Long id;
private String name;
private String shortName;
private String country;
private String city;
private String streetAddress;
private String postalCode;
private String stateProvince;
}
