package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.Booking;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.mapper.BookingMapper;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.repository.BookingRepository;
import it.freni.bookingbrakes.service.BookingService;
import it.freni.bookingbrakes.service.CustomerService;
import it.freni.bookingbrakes.service.SeatService;
import it.freni.bookingbrakes.service.TripService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@Log
public class BookingController {
    public final SeatService seatService;
    public final SeatMapper seatMapper;
    public final CustomerService customerService;
    public final CustomerMapper customerMapper;
    public final TripService tripService;
    public final TripMapper tripMapper;
    public final BookingService bookingService;
    public final BookingMapper bookingMapper;

    public BookingController(SeatService seatService, SeatMapper seatMapper, CustomerService customerService, CustomerMapper customerMapper, TripService tripService, TripMapper tripMapper, BookingService bookingService, BookingMapper bookingMapper) {
        this.seatService = seatService;
        this.seatMapper = seatMapper;
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.tripService = tripService;
        this.tripMapper = tripMapper;
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<BookingDto>> getBooking(){

        Iterable<BookingDto> bookingDtos = bookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingDtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable("id") Long id){

    Optional<Booking> booking = bookingService.findById(id);

        if(booking.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bookingMapper.bookingToBookingDto(booking.get()));
        }


        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<BookingDtoOut> postBooking(@RequestBody BookingDtoIn bookingDtoIn) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.saveBooking(bookingDtoIn));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookingDtoOut> putBooking(@PathVariable("id") Long Id, @RequestBody  BookingDtoIn bookingDtoIn) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookingService.replaceBooking(Id, bookingDtoIn));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable("id") Long id){
        bookingService.deleteBookingById(id);
    }
}
