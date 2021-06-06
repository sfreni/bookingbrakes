package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.SeatDto;
import it.freni.bookingbrakes.controller.dto.SeatDtoIn;
import it.freni.bookingbrakes.controller.dto.SeatDtoOut;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.service.SeatService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/seats/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable("id") Long id){
        seatService.deleteSeatById(id);
    }




}
