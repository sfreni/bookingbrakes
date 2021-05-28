package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.TripDto;
import it.freni.bookingbrakes.domain.Airplane;
import it.freni.bookingbrakes.domain.Airport;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.service.AirplaneService;
import it.freni.bookingbrakes.service.AirportService;
import it.freni.bookingbrakes.service.TripService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;

@RestController
@RequestMapping("/trips")
@Log
public class TripController {

    private final TripService tripService;
    private final AirportService airportService;
    private final AirplaneService airplaneService;
    private TripMapper tripMapper;

    public TripController(TripService tripService, AirportService airportService, AirplaneService airplaneService, TripMapper tripMapper) {
        this.tripService = tripService;
        this.airportService = airportService;
        this.airplaneService = airplaneService;
        this.tripMapper = tripMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<TripDto>> getAllTrips() {
        Iterable<TripDto> tripDtos = tripService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(tripDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getTripById(@PathVariable("id") Long id) {
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tripMapper.toDto(trip.get()));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TripDto> postTrip(@RequestBody TripDto tripDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tripService.saveTrip(tripMapper.dtoToTrip(tripDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDto> putTrip(@RequestBody TripDto tripDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tripService.replaceTrip(tripMapper.dtoToTrip(tripDto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TripDto> patchTrip(@PathVariable("id") Long id, @RequestBody TripDto tripDto){
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            if (tripDto.getStartDateFlight() != null) {
                trip.get().setStartDateFlight(tripDto.getStartDateFlight());
            }
            checkEndDateFlight(tripDto, trip);
            checkAirplane(tripDto, trip);
            if (tripDto.getDeparture() != null) {
                Optional<Airport> airport = airportService
                        .findById(tripMapper.airportDtoToAirport(tripDto.getDeparture()).getId());
                if (airport.isEmpty()) {
                    log.log(Level.SEVERE, "Departure Airport not found");
                    throw new NotObjectFound("Departure Airport not found");
                }
                trip.get().setDeparture(airport.get());
            }
            if (tripDto.getDestination() != null) {
                Optional<Airport> airport = airportService
                        .findById(tripMapper.airportDtoToAirport(tripDto.getDestination()).getId());
                if (airport.isEmpty()) {
                    log.log(Level.SEVERE, "Destination Airport not found");
                    throw new NotObjectFound("Destination Airport not found");
                }
                trip.get().setDestination(airport.get());
            }
            if (tripDto.getStatusTrip() != null) {
                trip.get().setStatusTrip(tripDto.getStatusTrip());
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tripService.modifyTrip(trip.get()));
        }
        log.log(Level.SEVERE, "Trip not found");
        throw new NotObjectFound("Trip not found");

    }

    private void checkEndDateFlight(TripDto tripDto, Optional<Trip> trip) {
        if (trip.isPresent() && tripDto.getEndDateFlight() != null) {
            trip.get().setEndDateFlight(tripDto.getEndDateFlight());
        }
    }

    private void checkAirplane(TripDto tripDto, Optional<Trip> trip) {
        if (tripDto.getAirplane() != null) {
            Optional<Airplane> airplane = airplaneService
                    .findById(tripMapper.airplaneDtoToAirplane(tripDto.getAirplane()).getId());
            checkAirplaneIsEmpty(airplane);
            if(trip.isPresent()){
                trip.get().setAirplane(airplane.get());
            }
        }
    }

    private void checkAirplaneIsEmpty(Optional<Airplane> airplane) {
        if (airplane.isEmpty()) {
            log.log(Level.SEVERE, "Airplane not found");
            throw new NotObjectFound("Airplane not found");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        tripService.deleteTripById(id);
    }


}
