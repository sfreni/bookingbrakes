package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.SeatDto;
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

    @GetMapping
    public ResponseEntity<Iterable<SeatDto>> getAllSeats(){
        Iterable<SeatDto> seatDtos = seatService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(seatDtos);

    }
    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getSeat(@PathVariable("id") Long id){
        Optional<Seat> dto = seatService.findById(id);
        if(dto.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(seatMapper.toDto(dto.get()));
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<SeatDtoOut> postSeat(@RequestBody SeatDto seatDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seatService.saveSeat(seatDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDto> putSeat(@RequestBody SeatDto seatDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(seatService.replaceSeat(seatMapper.dtoToSeat(seatDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable("id") Long id){
        seatService.deleteSeatById(id);
    }
}
