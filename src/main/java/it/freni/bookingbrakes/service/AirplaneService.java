package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.AirplaneDto;
import it.freni.bookingbrakes.domain.Airplane;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.AirplaneMapper;
import it.freni.bookingbrakes.repository.AirplaneRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Log
@Service
public class AirplaneService {
    public static final String AIRPLANE_NOT_FOUND_MESSAGE = "Airplane not found";
    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper mapper;

    public AirplaneService(AirplaneRepository airplaneRepository, AirplaneMapper mapper) {
        this.airplaneRepository = airplaneRepository;
        this.mapper = mapper;
    }

    public Iterable<AirplaneDto> findAll() {
        return mapper.toDtos(airplaneRepository.findAll());
    }

    public Optional<Airplane> findById(Long id) {
        return airplaneRepository.findById(id);
    }

    public AirplaneDto saveAirplane(Airplane airplane) {
        if (airplane.getId() != null && findById(airplane.getId()).isPresent()) {
            log.log(Level.SEVERE, "Id already exists");
            throw new IdAlreadyExists("Id already Exists");
        }
        return mapper.toDto(airplaneRepository.save(airplane));
    }

    public AirplaneDto replaceAirplane(Long id, Airplane airplane) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND_MESSAGE);
            throw new NotObjectFound(AIRPLANE_NOT_FOUND_MESSAGE);
        }
        airplane.setId(id);
        return mapper.toDto(airplaneRepository.save(airplane));
    }

    public void deleteAirplaneById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND_MESSAGE);
            throw new NotObjectFound(AIRPLANE_NOT_FOUND_MESSAGE);
        }
        airplaneRepository.deleteById(id);
    }

}
