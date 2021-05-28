package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Long> {
}
