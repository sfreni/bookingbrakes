package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.domain.Airplane;
import it.freni.bookingbrakes.mapper.AirplaneMapper;
import it.freni.bookingbrakes.service.AirplaneService;
import it.freni.bookingbrakes.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/airplanes")
public class AirplaneController {
    private final AirplaneService airplaneService;
    private final AirplaneMapper airplaneMapper;
private final TripService tripService;

    public AirplaneController(AirplaneService airplaneService, AirplaneMapper airplaneMapper, TripService tripService) {
        this.airplaneService = airplaneService;
        this.airplaneMapper = airplaneMapper;
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AirplaneDto>> getAllAirplanes(){
        Iterable<AirplaneDto> airplanes = airplaneService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(airplanes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirplaneDto> getAirplane(@PathVariable("id") Long id){
        Optional<Airplane> dto = airplaneService.findById(id);
        if(dto.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(airplaneMapper.toDto(dto.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<AirplaneDto> postAirplane(@RequestBody AirplaneDto airplaneDto)  {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(airplaneMapper.toDto(airplaneService.saveAirplane(airplaneMapper.dtoToAirplane(airplaneDto))));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AirplaneDto> putAirplane(@PathVariable("id")Long id, @RequestBody AirplaneDto airplaneDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(airplaneService.replaceAirplane(id,airplaneMapper.dtoToAirplane(airplaneDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAirplane(@PathVariable("id") Long id) {
        Optional<Airplane> airplane = airplaneService.findById(id);
        if (airplane.isPresent()) {
            if(tripService.findTripByAirplane(id)){
                airplaneService.deleteAirplaneById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            airplaneService.errorTripPresent();

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
