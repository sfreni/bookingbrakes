package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.domain.Airplane;
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
    private static final String DELETE_NOT_POSSIBILE_WITH_TRIP = "You can't delete an Airplane if it's got trips";
    private static final String AIRPLANE_NOT_FOUND = "Airplane not found";
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

    public Airplane findByIdWithoutOptional(Long id) {
        Optional<Airplane> airplane = airplaneRepository.findById(id);
        if(airplane.isPresent()){
            return airplane.get();
        }
        log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
        throw new NotObjectFound(AIRPLANE_NOT_FOUND);
    }

    public Airplane saveAirplane(Airplane airplane) {
        airplane.setId(null);
        return airplaneRepository.save(airplane);
    }

    public AirplaneDto replaceAirplane(Long id, Airplane airplane) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, AIRPLANE_NOT_FOUND);
            throw new NotObjectFound(AIRPLANE_NOT_FOUND);
        }
        airplane.setId(id);
        return mapper.toDto(airplaneRepository.save(airplane));
    }

    public void deleteAirplaneById(Long id) {

        airplaneRepository.deleteById(id);
    }
    public void errorTripPresent(){
        log.log(Level.SEVERE, DELETE_NOT_POSSIBILE_WITH_TRIP);
        throw new NotObjectFound(DELETE_NOT_POSSIBILE_WITH_TRIP);

    }

}
