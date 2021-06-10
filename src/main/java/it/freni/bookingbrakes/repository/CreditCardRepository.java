package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    List<CreditCard> findByCustomer(Customer customer);
}
