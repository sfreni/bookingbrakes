package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.SeatDto;
import it.freni.bookingbrakes.controller.dto.SeatDtoIn;
import it.freni.bookingbrakes.controller.dto.SeatDtoOut;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.repository.SeatRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
@Log
@Service
public class SeatService {
    public static final String SEAT_NOT_FOUND = "Seat not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer Not Found";
    public static final String TRIP_NOT_FOUND = "Trip not found";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    private final CustomerService customerService;
    private final SeatRepository seatRepository;
    private final TripService tripService;
    private final SeatMapper seatMapper;

    public SeatService(CustomerService customerService, SeatRepository seatRepository, TripService tripService, SeatMapper seatMapper) {
        this.customerService = customerService;
        this.seatRepository = seatRepository;
        this.tripService = tripService;
        this.seatMapper = seatMapper;
    }

    public Iterable<SeatDto> findAll(){
        return seatMapper.toDtos(seatRepository.findAll());

    }
    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }


    public SeatDtoOut saveSeat(SeatDtoIn seatDtoIn) {


        if (seatDtoIn.getId() != null && findById(seatDtoIn.getId()).isPresent()) {
            log.log(Level.SEVERE, ID_ALREADY_EXISTS);
            throw new IdAlreadyExists( ID_ALREADY_EXISTS);
        }

        if (customerService.findById(seatDtoIn.getCustomer().getId()).isEmpty()) {
            log.log(Level.SEVERE, CUSTOMER_NOT_FOUND);
            throw new NotObjectFound(CUSTOMER_NOT_FOUND);
        }

        Optional<Trip> trip = tripService.findById(seatDtoIn.getTrip().getId());
        if(trip.isEmpty()){
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }
        SeatDtoOut seatDtoOut =seatMapper.seatAndTripToDto(seatRepository.save(seatMapper.dtoInToSeat(seatDtoIn)),trip.get());
        trip.get().getSeats().add(seatMapper.dtoInToSeat(seatDtoIn));
        tripService.saveTripSeats(trip.get());
        //        SeatDtoOut seatDtoDestination  = seatMapper.toDtoOut(seatRepository.save(seatMapper.dtoToSeat(seatDto)));
        //seatDtoDestination = seatMapper.tripToDtoOut( seatDtoDestination, trip.get());
        return seatDtoOut;
    }


    public SeatDto replaceSeat(Seat seat) {

        if (seat.getId() == null || findById(seat.getId()).isEmpty()) {
            log.log(Level.SEVERE, SEAT_NOT_FOUND);
            throw new NotObjectFound(SEAT_NOT_FOUND);
        }

        if (customerService.findById(seat.getCustomer().getId()).isEmpty()) {
            log.log(Level.SEVERE, CUSTOMER_NOT_FOUND);
            throw new NotObjectFound(CUSTOMER_NOT_FOUND);
        }

        return seatMapper.toDto(seatRepository.save(seat));
    }

    public void deleteSeatById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, SEAT_NOT_FOUND);
            throw new NotObjectFound(SEAT_NOT_FOUND);
        }
        seatRepository.deleteById(id);
    }

}
