package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
