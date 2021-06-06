package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Booking;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTrip(Trip trip);

}
