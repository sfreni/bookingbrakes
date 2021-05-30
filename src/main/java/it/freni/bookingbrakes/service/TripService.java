package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.TripDto;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.error.IdAlreadyExists;
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
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    public static final String METHOD_NOT_ALLOWED_BECAUSE_SEATS_ALREADY_BOOKED = "Method not allowed because seats already booked";
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
        if (airplaneService.findById(trip.getAirplane().getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound( AIRPLANE_NOT_FOUND);
        }
        if (airportService.findById(trip.getDeparture().getId()).isEmpty()) {
            log.log(Level.SEVERE, DEPARTURE_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DEPARTURE_AIRPORT_NOT_FOUND);
        }
        if (airportService.findById(trip.getDestination().getId()).isEmpty()) {
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

        if (airplaneService.findById(trip.getAirplane().getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound( AIRPLANE_NOT_FOUND);
        }

        if (airportService.findById(trip.getDestination().getId()).isEmpty()) {
            log.log(Level.SEVERE, DESTINATION_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DESTINATION_AIRPORT_NOT_FOUND);
        }

        if (airportService.findById(trip.getDeparture().getId()).isEmpty()) {
            log.log(Level.SEVERE, DEPARTURE_AIRPORT_NOT_FOUND);
            throw new NotObjectFound(DEPARTURE_AIRPORT_NOT_FOUND);
        }

        if(!tripRepository.findById(trip.getId()).get()
                                                 .getSeats()
                                                 .isEmpty()) {

            trip.setSeats(tripRepository.findById(trip.getId()).get()
                    .getSeats());
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

    public TripDto saveTripSeats(Trip trip) {
        return tripMapper.toDto(tripRepository.save(trip));

    }


    }
