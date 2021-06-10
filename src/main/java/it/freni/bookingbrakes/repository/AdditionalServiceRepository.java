package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService,Long> {
}
