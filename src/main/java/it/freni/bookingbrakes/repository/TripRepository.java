package it.freni.bookingbrakes.repository;

import it.freni.bookingbrakes.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip,Long> {
        Optional<List<Trip>> findTripByAirplane_id(Long id);
        Optional<List<Trip>> findTripByDeparture_Id(Long id);
        Optional<List<Trip>> findTripByDestination_Id(Long id);

}
