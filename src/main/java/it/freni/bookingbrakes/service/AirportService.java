package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.AirportDto;
import it.freni.bookingbrakes.domain.Airport;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.AirportMapper;
import it.freni.bookingbrakes.repository.AirportRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class AirportService {
    public static final String AIRPORT_NOT_FOUND = "Airport not found";
    private final AirportRepository airportRepository;
    private AirportMapper airportMapper;

    public AirportService(AirportRepository airportRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }

    public Iterable<AirportDto> getAllAirports(){
        return airportMapper.toDtos(airportRepository.findAll());
    }

    public Optional<Airport> findById(Long id) {
        return airportRepository.findById(id);
    }

    public AirportDto saveAirport(Airport airport) {
        if (airport.getId() != null && findById(airport.getId()).isPresent()) {
            log.log(Level.SEVERE, "Id already exists");
            throw new IdAlreadyExists( "Id already Exists");
        }
        return airportMapper.toDto(airportRepository.save(airport));
    }

    public AirportDto replaceAirport(Airport airport) {
        if (airport.getId() == null || findById(airport.getId()).isEmpty()) {
            log.log(Level.SEVERE, AIRPORT_NOT_FOUND);
            throw new NotObjectFound(AIRPORT_NOT_FOUND);
        }
        return airportMapper.toDto(airportRepository.save(airport));
    }

    public void deleteAirportById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, AIRPORT_NOT_FOUND);
            throw new NotObjectFound(AIRPORT_NOT_FOUND);
        }
        airportRepository.deleteById(id);
    }




}
