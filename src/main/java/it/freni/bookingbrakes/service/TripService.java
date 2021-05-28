package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.TripDto;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.repository.AirplaneRepository;
import it.freni.bookingbrakes.repository.AirportRepository;
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
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    public static final String METHOD_NOT_ALLOWED_BECAUSE_SEATS_ALREADY_BOOKED = "Method not allowed because seats already booked";
    private final TripRepository tripRepository;
    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;
    private TripMapper tripMapper;

    public TripService(TripRepository tripRepository, AirportRepository airportRepository, AirplaneRepository airplaneRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.airportRepository = airportRepository;
        this.airplaneRepository = airplaneRepository;
        this.tripMapper = tripMapper;
    }

    public Iterable<TripDto> findAll(){
        return tripMapper.toDtos(tripRepository.findAll());
    }

    public Optional<Trip> findById(Long id) {
        return tripRepository.findById(id);
    }


    public TripDto saveTrip(Trip trip) {
        if (trip.getId() != null && findById(trip.getId()).isPresent()) {
            log.log(Level.SEVERE, ID_ALREADY_EXISTS);
            throw new IdAlreadyExists( ID_ALREADY_EXISTS);
        }
        if (airplaneRepository.findById(trip.getAirplane().getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound( AIRPLANE_NOT_FOUND);
        }
        if (airportRepository.findById(trip.getDeparture().getId()).isEmpty()) {
            log.log(Level.SEVERE, DEPARTURE_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DEPARTURE_AIRPORT_NOT_FOUND);
        }
        if (airportRepository.findById(trip.getDestination().getId()).isEmpty()) {
            log.log(Level.SEVERE, DESTINATION_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DESTINATION_AIRPORT_NOT_FOUND);
        }
        return tripMapper.toDto(tripRepository.save(trip));
    }



    public TripDto replaceTrip(Trip trip) {

        if (trip.getId() == null || findById(trip.getId()).isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound( TRIP_NOT_FOUND);
        }

        if (airplaneRepository.findById(trip.getAirplane().getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound( AIRPLANE_NOT_FOUND);
        }

        if (airportRepository.findById(trip.getDestination().getId()).isEmpty()) {
            log.log(Level.SEVERE, DESTINATION_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DESTINATION_AIRPORT_NOT_FOUND);
        }

        if (airportRepository.findById(trip.getDeparture().getId()).isEmpty()) {
            log.log(Level.SEVERE, DEPARTURE_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DEPARTURE_AIRPORT_NOT_FOUND);
        }

        if(!tripRepository.findById(trip.getId()).get()
                                                 .getSeats()
                                                 .isEmpty()) {
                log.log(Level.SEVERE, METHOD_NOT_ALLOWED_BECAUSE_SEATS_ALREADY_BOOKED);
                throw new NotObjectFound(METHOD_NOT_ALLOWED_BECAUSE_SEATS_ALREADY_BOOKED);
            }

        return tripMapper.toDto(tripRepository.save(trip));
    }


    public TripDto modifyTrip(Trip trip) {

        return tripMapper.toDto(tripRepository.save(trip));
    }

    public void deleteTripById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound( TRIP_NOT_FOUND);
        }
        tripRepository.deleteById(id);
    }


}
