package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport,Long> {
}
