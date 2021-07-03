package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.controller.dto.airport.AirportDto;
import it.freni.bookingbrakes.controller.dto.trip.PurchaseWithoutTripDto;
import it.freni.bookingbrakes.controller.dto.trip.TripDto;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.NotObjectFound;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TripServiceTest {
    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripMapper tripMapper = new TripMapperImpl();

    @Mock
    private AirplaneService airplaneService;
    @Mock
    private AirportService airportService;

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
        tripList=new ArrayList<>();
        trip.setId(1L);
        trip.setTripStatus(TripStatus.AVAILABLE);
        trip.setStartDateFlight(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28"));
        trip.setEndDateFlight(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28")) ;
        airplane.setId(1L);
        departure.setId(1L);
        destination.setId(2L);
        purchase.setId(1L);
        purchases.add(purchase);
        trip.setAirplane(airplane);
        trip.setDeparture(departure);
        trip.setDestination(destination);
        trip.setPurchases(purchases);
        tripList.add(trip);
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

    @Test
    public void findByIdWithoutOptional() {
        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(trip));
        assertThat(tripService.findByIdWithoutOptional(trip.getId())).isEqualTo(trip);
        assertThrows(NotObjectFound.class,() ->tripService.findByIdWithoutOptional(2L));
        verify(tripRepository, times(2)).findById(any());

    }

@Test
    public void saveTrip() {
        when(tripRepository.save(trip)).thenReturn(trip);
        when(tripMapper.toDto(trip)).thenReturn(tripDto);
        when(airplaneService.findById(trip.getAirplane().getId())).thenReturn(Optional.ofNullable(airplane));
        when(airportService.findById(trip.getDeparture().getId())).thenReturn(Optional.ofNullable(departure));
        when(airportService.findById(trip.getDestination().getId())).thenReturn(Optional.ofNullable(destination));
    TripDto tripDto2= tripService.saveTrip(trip);
    assertEquals(tripDto,tripDto2);


        verify(tripRepository, times(1)).save(trip);
        verify(tripMapper, times(1)).toDto(trip);
    }

    @Test
    public void saveTripWithError() {
        when(airplaneService.findById(trip.getAirplane().getId())).thenReturn(Optional.ofNullable(airplane));
        when(airportService.findById(trip.getDeparture().getId())).thenReturn(Optional.ofNullable(departure));
        when(airportService.findById(trip.getDestination().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.saveTrip(trip));
        when(airportService.findById(trip.getDeparture().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.saveTrip(trip));
        when(airplaneService.findById(trip.getAirplane().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.saveTrip(trip));

    }


    @Test
    public void replaceTrip() {
        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(trip));
        when(tripRepository.save(trip)).thenReturn(trip);
        when(tripMapper.toDto(trip)).thenReturn(tripDto);
        when(airplaneService.findById(trip.getAirplane().getId())).thenReturn(Optional.ofNullable(airplane));
        when(airportService.findById(trip.getDeparture().getId())).thenReturn(Optional.ofNullable(departure));
        when(airportService.findById(trip.getDestination().getId())).thenReturn(Optional.ofNullable(destination));
        TripDto tripDto2= tripService.replaceTrip(trip.getId(),trip);
        assertEquals(tripDto,tripDto2);
        verify(tripRepository, times(1)).save(trip);
        verify(tripRepository, times(1)).findById(trip.getId());
        verify(tripMapper, times(1)).toDto(trip);
    }

    @Test
    public void replaceTripWithErrors() {
        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(trip));
        when(airplaneService.findById(trip.getAirplane().getId())).thenReturn(Optional.ofNullable(airplane));
        when(airportService.findById(trip.getDestination().getId())).thenReturn(Optional.ofNullable(destination));
        when(airportService.findById(trip.getDeparture().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.replaceTrip(trip.getId(),trip));
        when(airportService.findById(trip.getDestination().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.replaceTrip(trip.getId(),trip));
        when(airplaneService.findById(trip.getAirplane().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.replaceTrip(trip.getId(),trip));
        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.replaceTrip(trip.getId(),trip));

    }

    @Test
    void deleteTrip() {
        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(trip));
        doNothing().when(tripRepository).deleteById(1L);
        assertThrows(NotObjectFound.class,() ->tripService.deleteTripById(1L));
        List<Purchase> purchaseList2= new ArrayList<>();
        trip.setPurchases(purchaseList2);
        tripService.deleteTripById(1L);
        when(tripRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->tripService.deleteTripById(1L));

        verify(tripRepository, times(5)).findById(trip.getId());
        verify(tripRepository, times(1)).deleteById(trip.getId());
    }
    @Test
    void findTripByAirplane() {
        when(tripRepository.findTripByAirplane_id(trip.getId())).thenReturn(Optional.ofNullable(tripList));
        tripService.findTripByAirplane(trip.getId());
        verify(tripRepository, times(1)).findTripByAirplane_id(trip.getId());

    }

    @Test
    void findTripByAirport() {
        List<Trip> tripList2= new ArrayList<>();
        when(tripRepository.findTripByDeparture_Id((trip.getId()))).thenReturn(Optional.ofNullable(tripList2));
        when(tripRepository.findTripByDestination_Id((trip.getId()))).thenReturn(Optional.ofNullable(tripList2));

        tripService.findTripByAirport(trip.getId());
      //  verify(tripRepository, times(1)).findTripByDeparture_Id(trip.getId());
        verify(tripRepository, times(1)).findTripByDestination_Id(trip.getId());

    }

}