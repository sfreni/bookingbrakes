package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.SeatDto;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.service.SeatService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
