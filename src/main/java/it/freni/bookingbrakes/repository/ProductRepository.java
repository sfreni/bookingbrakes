package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
            void deleteProductByPurchase(Purchase purchase);
}
