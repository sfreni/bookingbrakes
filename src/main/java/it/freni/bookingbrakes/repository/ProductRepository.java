package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Booking;
import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
