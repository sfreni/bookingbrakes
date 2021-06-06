package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Booking;
import it.freni.bookingbrakes.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByBooking(Booking booking);

}
