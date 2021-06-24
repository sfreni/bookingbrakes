package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.AirportDto;
import it.freni.bookingbrakes.domain.Airport;
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
    private final String AIRPORT_NOT_FOUND = "Airport not found";
    private final String DELETE_NOT_POSSIBILE_WITH_TRIP = "You can't delete an Airport if it's got trips";
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

        airport.setId(null);
        return airportMapper.toDto(airportRepository.save(airport));
    }

    public AirportDto replaceAirport(Long id, Airport airport) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, AIRPORT_NOT_FOUND);
            throw new NotObjectFound(AIRPORT_NOT_FOUND);

        }
        airport.setId(id);
        return airportMapper.toDto(airportRepository.save(airport));
    }

    public void deleteAirportById(Long id) {

        airportRepository.deleteById(id);
    }

    public void errorTripPresent(){
            log.log(Level.SEVERE, DELETE_NOT_POSSIBILE_WITH_TRIP);
            throw new NotObjectFound(DELETE_NOT_POSSIBILE_WITH_TRIP);

    }



}
