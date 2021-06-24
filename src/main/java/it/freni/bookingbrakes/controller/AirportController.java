package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.airport.AirportDto;
import it.freni.bookingbrakes.domain.Airport;
import it.freni.bookingbrakes.mapper.AirportMapper;
import it.freni.bookingbrakes.service.AirportService;
import it.freni.bookingbrakes.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;
    private final AirportMapper airportMapper;
    private final TripService tripService;

    public AirportController(AirportService airportService, AirportMapper airportMapper, TripService tripService) {
        this.airportService = airportService;
        this.airportMapper = airportMapper;
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AirportDto>> getAllAirports(){
        Iterable<AirportDto> airportDtos = airportService.getAllAirports();
        return ResponseEntity.status(HttpStatus.OK)
                .body(airportDtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDto> getAirport(@PathVariable("id") Long id){
        Optional<Airport> airport = airportService.findById(id);
        if(airport.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(airportMapper.toDto(airport.get()));
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<AirportDto> postAirport(@RequestBody AirportDto airportDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(airportService.saveAirport(airportMapper.dtoToAirport(airportDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportDto> putAirport(@PathVariable("id") Long id,  @RequestBody AirportDto airportDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(airportService.replaceAirport(id, airportMapper.dtoToAirport(airportDto)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAirport(@PathVariable("id") Long id) {
        Optional<Airport> airport = airportService.findById(id);
        if (airport.isPresent()) {
            if(tripService.findTripByAirport(id)){
                airportService.deleteAirportById(id);
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
             airportService.errorTripPresent();

        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }

}
