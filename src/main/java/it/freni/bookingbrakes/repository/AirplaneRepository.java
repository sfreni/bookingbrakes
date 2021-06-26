package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane,Long> {

}
