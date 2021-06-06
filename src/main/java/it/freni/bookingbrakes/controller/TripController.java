package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.service.*;
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
    private final BookingService bookingService;
    private final TripMapper tripMapper;
    private final SeatMapper  seatMapper;

    public TripController(TripService tripService, AirportService airportService, AirplaneService airplaneService, BookingService bookingService, TripMapper tripMapper, SeatMapper seatMapper) {
        this.tripService = tripService;
        this.airportService = airportService;
        this.airplaneService = airplaneService;
        this.bookingService = bookingService;
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

    @GetMapping("/bookings")
    public ResponseEntity<Iterable<BookingDto>> getBooking(){

        Iterable<BookingDto> bookingDtos = bookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingDtos);

        }

/*
    @GetMapping("/bookings/{id}")
    public ResponseEntity<SeatDtoOut> getSeat(@PathVariable("id") Long id){

        Optional<Seat> seat = bookingService.findById(id);

        if(seat.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(seatMapper.seatAndTripToDto(seat.get(),seat.get().getTrip()));
        }


        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/bookings")
    public ResponseEntity<SeatDtoOut> postSeat(@RequestBody SeatDtoIn seatDtoIn) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seatService.saveSeat(seatDtoIn));
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<SeatDtoOut> putSeat(@PathVariable("id") Long Id, @RequestBody SeatDtoIn seatDtoIn) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(seatService.replaceSeat(Id, seatDtoIn));
    }


    @DeleteMapping("/bookings/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable("id") Long id){
        seatService.deleteSeatById(id);
    }

*/
}
