package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.AdditionalServiceDto;
import it.freni.bookingbrakes.controller.dto.SeatDto;
import it.freni.bookingbrakes.domain.AdditionalService;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.mapper.AdditionalServiceMapper;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.repository.AdditionalServiceRepository;
import it.freni.bookingbrakes.service.SeatService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/additionalservices")
@Log
public class AdditionalServiceController {
    public final AdditionalServiceRepository additionalServiceRepository;
    public final AdditionalServiceMapper additionalServiceMapper;

    public AdditionalServiceController(AdditionalServiceRepository additionalServiceRepository, AdditionalServiceMapper additionalServiceMapper) {
        this.additionalServiceRepository = additionalServiceRepository;
        this.additionalServiceMapper = additionalServiceMapper;
    }

    @GetMapping
    public ResponseEntity<List<AdditionalServiceDto>> getAllAdditionalServices() {
        List<AdditionalService> additionalService = additionalServiceRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(additionalServiceMapper.toDtos(additionalService));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AdditionalServiceDto> getTripById(@PathVariable("id") Long id) {
        Optional<AdditionalService> additionalService = additionalServiceRepository.findById(id);
        if (additionalService.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(additionalServiceMapper.toDto(additionalService.get()));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

   @PostMapping
    public ResponseEntity<AdditionalServiceDto> postCustomer(@RequestBody AdditionalServiceDto additionalServiceDto) {
            additionalServiceDto.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(additionalServiceMapper.toDto(additionalServiceRepository.save(additionalServiceMapper.dtoToAdditionalService(additionalServiceDto))));
    }
/*

    @DeleteMapping("/seats/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable("id") Long id){
        seatService.deleteSeatById(id);
    }

*/


}
