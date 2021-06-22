package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.trip.TripDto;
import it.freni.bookingbrakes.controller.dto.trip.TripDtoOut;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.service.AirplaneService;
import it.freni.bookingbrakes.service.AirportService;
import it.freni.bookingbrakes.service.TripService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trips")
@Log
public class TripController {

    private final TripService tripService;
    private final AirportService airportService;
    private final AirplaneService airplaneService;
    private final TripMapper tripMapper;
    private final SeatMapper  seatMapper;

    public TripController(TripService tripService, AirportService airportService, AirplaneService airplaneService, TripMapper tripMapper, SeatMapper seatMapper) {
        this.tripService = tripService;
        this.airportService = airportService;
        this.airplaneService = airplaneService;
        this.tripMapper = tripMapper;
        this.seatMapper = seatMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<TripDto>> getAllTrips() {
        Iterable<TripDto> tripDtos = tripService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(tripDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TripDtoOut> getTripById(@PathVariable("id") Long id) {
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tripMapper.toDtoOut(trip.get()));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TripDto> postTrip(@RequestBody TripDto tripDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tripService.saveTrip(tripMapper.dtoToTrip(tripDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDtoOut> putTrip(@PathVariable("id") Long Id, @RequestBody TripDto tripDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tripService.replaceTrip(Id,tripMapper.dtoToTrip(tripDto)));
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        tripService.deleteTripById(id);
    }


}
