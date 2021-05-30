package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.AirplaneDto;
import it.freni.bookingbrakes.domain.Airplane;
import it.freni.bookingbrakes.mapper.AirplaneMapper;
import it.freni.bookingbrakes.service.AirplaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/airplanes")
public class AirplaneController {
    private final AirplaneService airplaneService;
    private  AirplaneMapper airplaneMapper;

    public AirplaneController(AirplaneService airplaneService, AirplaneMapper airplaneMapper) {
        this.airplaneService = airplaneService;
        this.airplaneMapper = airplaneMapper;
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
        return new ResponseEntity<>(new AirplaneDto(),HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<AirplaneDto> postAirplane(@RequestBody AirplaneDto airplaneDto)  {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(airplaneService.saveAirplane(airplaneMapper.dtoToAirplane(airplaneDto)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AirplaneDto> putAirplane(@PathVariable("id")Long id, @RequestBody AirplaneDto airplaneDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(airplaneService.replaceAirplane(id,airplaneMapper.dtoToAirplane(airplaneDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteAirplane(@PathVariable("id") Long id) {
        airplaneService.deleteAirplaneById(id);
    }

}
