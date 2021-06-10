package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.service.SeatService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seats")
@Log
public class SeatController {
    public final SeatService seatService;
    public final SeatMapper seatMapper;

    public SeatController(SeatService seatService, SeatMapper seatMapper) {
        this.seatService = seatService;
        this.seatMapper = seatMapper;
    }


    @GetMapping
    public ResponseEntity<List<SeatDto>> getAllTrips() {
        List<Seat> seat = seatService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(seatMapper.toDtos(seat));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getTripById(@PathVariable("id") Long id) {
        Optional<Seat> seat = seatService.findById(id);
        if (seat.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(seatMapper.toDto(seat.get()));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<SeatDto> postCustomer(@RequestBody SeatDto seatDto) {
            seatDto.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seatMapper.toDto(seatService.saveSeat(seatMapper.dtoToSeat(seatDto))));
    }

/*
    @DeleteMapping("/seats/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable("id") Long id){
        seatService.deleteSeatById(id);
    }

*/


}
