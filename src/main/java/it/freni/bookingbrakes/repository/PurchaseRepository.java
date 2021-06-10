package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
