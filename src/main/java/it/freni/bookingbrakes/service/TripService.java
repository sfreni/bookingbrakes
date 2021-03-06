package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.trip.TripDto;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.repository.TripRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class TripService {
    public static final String TRIP_NOT_FOUND = "Trip not found";
    public static final String AIRPLANE_NOT_FOUND = "Airplane Not Found";
    public static final String DESTINATION_AIRPORT_NOT_FOUND = "Destination Airport Not Found";
    public static final String DEPARTURE_AIRPORT_NOT_FOUND = "Departure Airport Not Found";
    private static final String DELETE_FAILED_PURCHASE = "Can't delete this trip because it has purchases";
    private final TripRepository tripRepository;
    private final AirportService airportService;
    private final AirplaneService airplaneService;
    private TripMapper tripMapper;

    public TripService(TripRepository tripRepository, AirportService airportService, AirplaneService airplaneService, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.airportService = airportService;
        this.airplaneService = airplaneService;
        this.tripMapper = tripMapper;
    }

    public Iterable<TripDto> findAll() {
        return tripMapper.toDtos(tripRepository.findAll());
    }

    public Optional<Trip> findById(Long id) {
        return tripRepository.findById(id);
    }

    public Trip findByIdWithoutOptional(Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isPresent()){
            return trip.get();
        }
        log.log(Level.SEVERE, TRIP_NOT_FOUND);
        throw new NotObjectFound( TRIP_NOT_FOUND);
    }

    public TripDto saveTrip(Trip trip) {

        if (airplaneService.findById(trip.getAirplane().getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound(AIRPLANE_NOT_FOUND);
        }
        if (airportService.findById(trip.getDeparture().getId()).isEmpty()) {
            log.log(Level.SEVERE, DEPARTURE_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DEPARTURE_AIRPORT_NOT_FOUND);
        }
        if (airportService.findById(trip.getDestination().getId()).isEmpty()) {
            log.log(Level.SEVERE, DESTINATION_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DESTINATION_AIRPORT_NOT_FOUND);
        }
        trip.setId(null);
        trip.setAirplane(airplaneService.findByIdWithoutOptional(trip.getAirplane().getId()));
        trip.setDestination(airportService.findByIdWithoutOptional(trip.getDestination().getId()));
        trip.setDeparture(airportService.findByIdWithoutOptional(trip.getDeparture().getId()));
        return tripMapper.toDto(tripRepository.save(trip));
    }


    public TripDto replaceTrip(Long id, Trip trip) {
        Optional<Trip> tripDb=  findById(id);
        if (id == null || tripDb.isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }

        if (airplaneService.findById(trip.getAirplane().getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound(AIRPLANE_NOT_FOUND);
        }

        if (airportService.findById(trip.getDestination().getId()).isEmpty()) {
            log.log(Level.SEVERE, DESTINATION_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DESTINATION_AIRPORT_NOT_FOUND);
        }

        if (airportService.findById(trip.getDeparture().getId()).isEmpty()) {
            log.log(Level.SEVERE, DEPARTURE_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DEPARTURE_AIRPORT_NOT_FOUND);
        }
        trip.setId(id);
        if (!tripDb.get()
                .getPurchases()
                .isEmpty()) {

            trip.setPurchases(tripDb.get()
                    .getPurchases());
        }

        return tripMapper.toDto(tripRepository.save(trip));
    }


    public void deleteTripById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }
        if (!findByIdWithoutOptional(id).getPurchases().isEmpty()) {
            log.log(Level.SEVERE, DELETE_FAILED_PURCHASE);
            throw new NotObjectFound(DELETE_FAILED_PURCHASE);

        }
        tripRepository.deleteById(id);
    }

    public boolean findTripByAirplane(Long id) {

        return tripRepository.findTripByAirplane_id(id).get().isEmpty();

    }

    public boolean findTripByAirport(Long id) {

        return  tripRepository.findTripByDestination_Id(id).get().isEmpty() &&
                tripRepository.findTripByDeparture_Id(id).get().isEmpty()
                ;
    }
}
