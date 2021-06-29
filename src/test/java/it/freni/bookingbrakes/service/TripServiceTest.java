package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.controller.dto.airport.AirportDto;
import it.freni.bookingbrakes.controller.dto.trip.PurchaseWithoutTripDto;
import it.freni.bookingbrakes.controller.dto.trip.TripDto;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.mapper.TripMapperImpl;
import it.freni.bookingbrakes.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TripServiceTest {
    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripMapper tripMapper = new TripMapperImpl();

    @Autowired
    @InjectMocks
    private TripService tripService;

    private Trip trip;
    private List<Trip> tripList;
    private TripDto tripDto;
    private List<TripDto> tripListDto;

    private Airplane airplane;
    private Airport departure;
    private Airport destination;
    private List<Purchase> purchases;
    private Purchase purchase;

    private AirplaneDto airplaneDto;
    private AirportDto departureDto;
    private AirportDto destinationDto;
    private List<PurchaseWithoutTripDto> purchasesDto;
    private PurchaseWithoutTripDto purchaseDto;

    @BeforeEach
    void setUp() throws Exception {
        trip = new Trip();

          airplane= new Airplane();
          departure= new Airport();
          destination =new Airport();
          purchase = new Purchase();
        purchases= new ArrayList<>();
        trip.setId(1L);
        trip.setTripStatus(TripStatus.AVAILABLE);
        trip.setStartDateFlight(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28"));
        trip.setEndDateFlight(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28")) ;
        airplane.setId(1L);
        departure.setId(1L);
        destination.setId(1L);
        purchase.setId(1L);
        purchases.add(purchase);
        trip.setAirplane(airplane);
        trip.setDeparture(departure);
        trip.setDestination(destination);
        trip.setPurchases(purchases);

        tripDto= new TripDto();
        tripListDto = new ArrayList<>();
        airplaneDto= new AirplaneDto();
        departureDto= new AirportDto();
        destinationDto =new AirportDto();
        purchaseDto = new PurchaseWithoutTripDto();
        purchasesDto= new ArrayList<>();
        tripDto.setId(1L);
        tripDto.setTripStatus(TripStatus.AVAILABLE);
        tripDto.setStartDateFlight(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28"));
        tripDto.setEndDateFlight(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28")) ;
        airplaneDto.setId(1L);
        departureDto.setId(1L);
        destinationDto.setId(1L);
        purchaseDto.setId(1L);
        purchasesDto.add(purchaseDto);
        tripDto.setAirplane(airplaneDto);
        tripDto.setDeparture(departureDto);
        tripDto.setDestination(destinationDto);
        tripDto.setPurchases(purchasesDto);
        tripListDto.add(tripDto);


    }

    @Test
    void findTrip() {

        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(trip));
        assertThat(tripService.findById(trip.getId())).get().isEqualTo(trip);
        verify(tripRepository, times(1)).findById(any());
    }

    @Test
    void findAllTrips() {
        when(tripRepository.findAll()).thenReturn(tripList);
        when(tripMapper.toDtos(tripList)).thenReturn(tripListDto);

        List<TripDto> tripsDto = (List<TripDto>) tripService.findAll();
        assertEquals(tripsDto,tripListDto);
        verify(tripRepository, times(1)).findAll();

    }

}