package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip,Long> {
}
