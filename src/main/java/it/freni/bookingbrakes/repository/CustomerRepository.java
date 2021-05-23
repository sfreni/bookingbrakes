package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
