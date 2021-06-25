package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardTransactionRepository extends JpaRepository<CreditCardTransaction,Long> {
    List<CreditCardTransaction> findByCreditcard(CreditCard creditCard);
}
