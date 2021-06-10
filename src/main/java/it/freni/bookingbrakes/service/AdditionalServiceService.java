package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.domain.AdditionalService;
import it.freni.bookingbrakes.mapper.AdditionalServiceMapper;
import it.freni.bookingbrakes.repository.AdditionalServiceRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log
@Service
public class AdditionalServiceService {
    public static final String SEAT_NOT_FOUND = "Seat not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer Not Found";
    public static final String TRIP_NOT_FOUND = "Trip not found";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    private final CustomerService customerService;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final TripService tripService;
    private final AdditionalServiceMapper additionalServiceMapper;

    public AdditionalServiceService(CustomerService customerService, AdditionalServiceRepository additionalServiceRepository, TripService tripService, AdditionalServiceMapper additionalServiceMapper) {
        this.customerService = customerService;
        this.additionalServiceRepository = additionalServiceRepository;
        this.tripService = tripService;
        this.additionalServiceMapper = additionalServiceMapper;
    }

    public List<AdditionalService> findAll() {
        return additionalServiceRepository.findAll();
    }

    public Optional<AdditionalService> findById(Long id) {
         return additionalServiceRepository.findById(id);
    }

 //   public List<Purchase> findByBooking(Purchase purchase) {
    //    List<Seat> seats = seatRepository.findByBooking(booking);

     //   return seatRepository.findByBooking(order);
  //  return null;
   // }



    public AdditionalService saveSeat(AdditionalService additionalService) {


     /*   if (Seat.getId() != null && findById(Seat.getId()).isPresent()) {
            log.log(Level.SEVERE, ID_ALREADY_EXISTS);
            throw new IdAlreadyExists( ID_ALREADY_EXISTS);
        }*/


        return additionalServiceRepository.save(additionalService);
    }



/*

    public void deleteSeatById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, SEAT_NOT_FOUND);
            throw new NotObjectFound(SEAT_NOT_FOUND);
        }
        seatRepository.deleteById(id);
    }

    public void deleteAllSeat(List<Seat> seats){
        seatRepository.deleteAll(seats);
    }*/

}
