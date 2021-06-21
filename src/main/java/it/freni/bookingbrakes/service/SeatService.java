package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.repository.SeatRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public Optional<Seat> findById(Long id) {
         return seatRepository.findById(id);
    }

 //   public List<Purchase> findByBooking(Purchase purchase) {
    //    List<Seat> seats = seatRepository.findByBooking(booking);

     //   return seatRepository.findByBooking(order);
  //  return null;
   // }



    public Seat saveSeat(Seat seat) {


     /*   if (Seat.getId() != null && findById(Seat.getId()).isPresent()) {
            log.log(Level.SEVERE, ID_ALREADY_EXISTS);
            throw new IdAlreadyExists( ID_ALREADY_EXISTS);
        }*/


        return seatRepository.save(seat);
    }





    public void deleteSeatById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, SEAT_NOT_FOUND);
            throw new NotObjectFound(SEAT_NOT_FOUND);
        }
        seatRepository.deleteById(id);
    }

    public void deleteAllSeat(List<Seat> seats){
        seatRepository.deleteAll(seats);
    }

}
