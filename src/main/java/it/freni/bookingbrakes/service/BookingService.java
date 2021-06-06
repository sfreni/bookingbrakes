package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.BookingDto;
import it.freni.bookingbrakes.controller.dto.BookingDtoIn;
import it.freni.bookingbrakes.controller.dto.BookingDtoOut;
import it.freni.bookingbrakes.domain.Booking;
import it.freni.bookingbrakes.domain.Seat;
import it.freni.bookingbrakes.domain.TripStatus;
import it.freni.bookingbrakes.domain.Trip;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.BookingMapper;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.mapper.SeatMapper;
import it.freni.bookingbrakes.repository.BookingRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Log
@Service
public class BookingService {
    public static final String SEAT_MISMATCH = "Seat ID is not consistent with this booking";
    public static final String CUSTOMER_NOT_FOUND = "Customer Not Found";
    public static final String CUSTOMER_NOT_EQUAL = "Inconsistent Data, customer persisted in DB is different ";
    public static final String TRIP_NOT_EQUAL = "Inconsistent Data, the persisted trip is different";
    public static final String TRIP_NOT_FOUND = "Trip Not Found";
    public static final String TRIP_NOT_AVAILABLE = "Trip Not Available";
    public static final String PRICE_NOT_SET = "Price of Booking is not set";

    public static final String ID_ALREADY_EXISTS = "Id already exists";
    public static final String ID_NOT_ALLOWED = "Id not allowed";
    public static final String SEAT_ALREADY_BOOKED = "Seat already Booked";
    public static final String SEAT_ALREADY_BOOKED_USE_CORRECT_ID = "Seat already Booked, please use correct ID to modify";
    public static final String SEATS_NOT_FOUND = "Seats not Found";
    public static final String BOOKING_NOT_FOUND = "Booking not Found";

    private final CustomerService customerService;
    private final BookingRepository bookingRepository;
    private final TripService tripService;
    private final SeatService seatService;

    private final BookingMapper bookingMapper;
    private final SeatMapper seatMapper;
    private final CustomerMapper customerMapper;

    public BookingService(CustomerService customerService, BookingRepository bookingRepository, TripService tripService, SeatService seatService, BookingMapper bookingMapper, SeatMapper seatMapper, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.bookingRepository = bookingRepository;
        this.tripService = tripService;
        this.seatService = seatService;
        this.bookingMapper = bookingMapper;
        this.seatMapper = seatMapper;
        this.customerMapper = customerMapper;
    }

    public Iterable<BookingDto> findAll() {

        return bookingMapper.listToListDtos(bookingRepository.findAll());
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public BookingDtoOut saveBooking(BookingDtoIn bookingDtoIn) {


        if (bookingDtoIn.getId() != null) {
            log.log(Level.SEVERE, ID_NOT_ALLOWED);
            throw new IdAlreadyExists(ID_NOT_ALLOWED);
        }


        if (customerService.findById(bookingDtoIn.getCustomer().getId()).isEmpty()) {
            log.log(Level.SEVERE, CUSTOMER_NOT_FOUND);
            throw new NotObjectFound(CUSTOMER_NOT_FOUND);
        }
        if (bookingDtoIn.getSeats().isEmpty()) {
            log.log(Level.SEVERE, SEATS_NOT_FOUND);
            throw new NotObjectFound(SEATS_NOT_FOUND);
        }

        Optional<Trip> trip = tripService.findById(bookingDtoIn.getTrip().getId());
        if (trip.isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }

        if(trip.get().getTripStatus()!=TripStatus.AVAILABLE){
            System.out.println(trip.get().getTripStatus());
            log.log(Level.SEVERE, TRIP_NOT_AVAILABLE);
            throw new NotObjectFound(TRIP_NOT_AVAILABLE);

        }
        List<Seat> seatsBooking = bookingDtoIn.getSeats();
        List<Booking> bookings = findByBooking(bookingDtoIn.getTrip());
        for (Booking booking : bookings) {
            for (Seat seat : booking.getSeats()) {
                for (Seat seatValuated : seatsBooking) {
                    if (seatValuated.getNrSeat().equals(seat.getNrSeat())) {
                        log.log(Level.SEVERE, SEAT_ALREADY_BOOKED);
                        throw new IdAlreadyExists(SEAT_ALREADY_BOOKED);
                    }
                }
            }
        }
        bookingDtoIn.setDateBooking(new Date(System.currentTimeMillis()));
        Booking booking = bookingRepository.save(bookingMapper.bookingDtoInToBooking(bookingDtoIn));
        for (Seat seat : seatsBooking) {
            seat.setBooking(booking);
        }

        return bookingMapper.bookingAndTripToDto(booking, trip.get(), seatMapper.toDtos(seatService.saveSeat(seatsBooking)),
                customerMapper.toDto(customerService.findById(bookingDtoIn.getCustomer().getId()).get()));
    }

    public BookingDtoOut replaceBooking(Long id, BookingDtoIn bookingDtoIn) {
        bookingDtoIn.setId(id);
        Optional<Booking> bookingFromDB = bookingRepository.findById(id);

        if (bookingFromDB.isEmpty()) {
            log.log(Level.SEVERE, BOOKING_NOT_FOUND);
            throw new NotObjectFound( BOOKING_NOT_FOUND);
        }


        if (customerService.findById(bookingDtoIn.getCustomer().getId()).isEmpty()) {
            log.log(Level.SEVERE, CUSTOMER_NOT_FOUND);
            throw new NotObjectFound(CUSTOMER_NOT_FOUND);
        } else if (bookingDtoIn.getCustomer().getId() != bookingFromDB.get().getCustomer().getId()) {
            log.log(Level.SEVERE, CUSTOMER_NOT_EQUAL);
            throw new NotObjectFound(CUSTOMER_NOT_EQUAL);
        }


        Optional<Trip> trip = tripService.findById(bookingDtoIn.getTrip().getId());
        if (trip.isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        } else if (trip.get().getId() != bookingFromDB.get().getTrip().getId()) {
            log.log(Level.SEVERE, TRIP_NOT_EQUAL);
            throw new NotObjectFound(TRIP_NOT_EQUAL);
        }

        if(!trip.get().getTripStatus().equals(TripStatus.AVAILABLE)){
            log.log(Level.SEVERE, TRIP_NOT_AVAILABLE);
            throw new NotObjectFound(TRIP_NOT_AVAILABLE);

        }

        if (bookingDtoIn.getSeats().isEmpty()) {
            log.log(Level.SEVERE, SEATS_NOT_FOUND);
            throw new NotObjectFound(SEATS_NOT_FOUND);
        }

         for (Seat seat : bookingDtoIn.getSeats()) {
            if ((seat.getId() != null && seatService.findById(seat.getId()).isEmpty())
                    || (seat.getId() != null && seatService.findById(seat.getId()).get().getBooking().getId()
                    != id)) {
                log.log(Level.SEVERE, SEAT_MISMATCH);
                throw new IdAlreadyExists(SEAT_MISMATCH);
            }


        }


        List<Booking> bookings = findByBooking(bookingFromDB.get().getTrip());
        for (Booking booking : bookings) {
            for (Seat seatDb : booking.getSeats())
                for (Seat seatValuated : bookingDtoIn.getSeats()) {

                    if (seatValuated.getNrSeat().equals(seatDb.getNrSeat()) &&
                            seatDb.getBooking().getId() != id) {
                        log.log(Level.SEVERE, SEAT_ALREADY_BOOKED);
                        throw new IdAlreadyExists(SEAT_ALREADY_BOOKED);
                    }

                    if (seatValuated.getNrSeat().equals(seatDb.getNrSeat()) &&
                            seatValuated.getId() != seatDb.getId()) {
                        log.log(Level.SEVERE, SEAT_ALREADY_BOOKED_USE_CORRECT_ID);
                        throw new IdAlreadyExists(SEAT_ALREADY_BOOKED_USE_CORRECT_ID);
                    }


                }
        }

        List<Seat> seats = bookingFromDB.get().getSeats();
        List<Seat> seatsBooking = bookingDtoIn.getSeats();
        boolean isPresent = false;
        for (Seat seat : seats) {
            for (Seat seatBooking : seatsBooking) {
                if (seatBooking.getId()==seat.getId()) {
                    isPresent = true;
                }
                System.out.println(seatBooking.getNrSeat());
                System.out.println(seat.getNrSeat());

            }


            if (!isPresent && seatService.findById(seat.getId()).isPresent()) {
                seatService.deleteSeatById(seat.getId());
            }
            isPresent = false;

        }

        for (Seat seat : seatsBooking) {
            seat.setBooking(bookingMapper.bookingDtoInToBooking(bookingDtoIn));
        }

        bookingDtoIn.setSeats(seatsBooking);
        Booking booking = bookingRepository.save(bookingMapper.bookingDtoInToBooking(bookingDtoIn));


        List<Seat> savedSet = seatService.saveSeat(seatsBooking);


        return bookingMapper.bookingAndTripToDto(booking, trip.get(), seatMapper.toDtos(savedSet),
                customerMapper.toDto(customerService.findById(bookingDtoIn.getCustomer().getId()).get()));
    }


    public void deleteBookingById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, BOOKING_NOT_FOUND);
            throw new NotObjectFound( BOOKING_NOT_FOUND);
        }
        deleteSeats(findById(id).get().getSeats());
        bookingRepository.deleteById(id);
    }

    public void deleteSeats(List<Seat>  seats) {
        if (seats.isEmpty()) {
            log.log(Level.SEVERE, SEATS_NOT_FOUND);
            throw new NotObjectFound( SEATS_NOT_FOUND);
        }

        seatService.deleteAllSeat(seats);
    }



    public List<Booking> findByBooking(Trip trip) {
        List<Booking> bookings = bookingRepository.findByTrip(trip);

        return bookingRepository.findByTrip(trip);
    }


}
