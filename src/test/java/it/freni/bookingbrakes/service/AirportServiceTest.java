package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.airport.AirportDto;
import it.freni.bookingbrakes.domain.Airport;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.AirportMapper;
import it.freni.bookingbrakes.mapper.AirportMapperImpl;
import it.freni.bookingbrakes.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;
    @Mock
    private AirportMapper mapper = new AirportMapperImpl();
    @Mock
    private TripService  tripService;

    @Autowired
    @InjectMocks
    private AirportService airportService;
    private Airport airport;
    private List<Airport> airportList;
    private AirportDto airportDto;
    private List<AirportDto> airportDtoList;
    @BeforeEach
    void setUp() {

        airport = new Airport();
        airport.setId(1L);
        airport.setName("Paris Charles de Gaulle Airport");
        airport.setCity("Paris");
        airport.setCountry("France");
        airport.setPostalCode("95700");
        airport.setShortName("CDG");
        airport.setStateProvince("Paris");
        airport.setStreetAddress("Roissy-en-France");
        airportList = new ArrayList<>();
        airportList.add(airport);

        airportDto = new AirportDto();
        airportDto.setId(1L);
        airportDto.setName("Paris Charles de Gaulle Airport");
        airportDto.setCity("Paris");
        airportDto.setCountry("France");
        airportDto.setPostalCode("95700");
        airportDto.setShortName("CDG");
        airportDto.setStateProvince("Paris");
        airportDto.setStreetAddress("Roissy-en-France");
        airportDtoList = new ArrayList<>();
        airportDtoList.add(airportDto);
    }

    @Test
    void getAirport() {

        when(airportRepository.findById(1L)).thenReturn(Optional.ofNullable(airport));
        assertThat(airportService.findById(airport.getId())).get().isEqualTo(airport);
        verify(airportRepository, times(1)).findById(any());
    }
    @Test
    void getAirportWithoutOptional() {

        when(airportRepository.findById(1L)).thenReturn(Optional.ofNullable(airport));
        when(airportRepository.findById(0L)).thenReturn(Optional.ofNullable(null));
        assertThat(airportService.findByIdWithoutOptional(1L)).isEqualTo(airport);
        assertThrows(NotObjectFound.class,() -> airportService.findByIdWithoutOptional(0L));
        verify(airportRepository, times(2)).findById(any());
    }
    @Test
    void getAllAirports() {
        when(airportRepository.findAll()).thenReturn(airportList);
        when(mapper.toDtos(airportList)).thenReturn(airportDtoList);
        when(mapper.dtosToAirports(airportDtoList)).thenReturn(airportList);

        List<Airport> airports = (List<Airport>) mapper.dtosToAirports(airportService.getAllAirports());

        assertEquals(airportList,airports);
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    void saveAirport() {
        when(airportRepository.save(airport)).thenReturn(airport);
        when(mapper.toDto(airport)).thenReturn(airportDto);
        when(mapper.dtoToAirport(airportDto)).thenReturn(airport);
        Airport airport2= mapper.dtoToAirport(airportService.saveAirport(airport));

        assertEquals(airport,airport2);
        verify(airportRepository, times(1)).save(airport);

    }
    @Test

    void replaceAirport() {
        //  airplaneRepository.save(airplane);
        when(airportRepository.findById(airport.getId())).thenReturn(Optional.ofNullable(airport));
        when(airportRepository.save(airport)).thenReturn(airport);
        when(mapper.toDto(airport)).thenReturn(airportDto);
        when(mapper.dtoToAirport(airportDto)).thenReturn(airport);

        airportDto= airportService.replaceAirport(1L, airport);
        Airport airport2 = mapper.dtoToAirport(airportDto);
        assertEquals(airport,airport2);
        verify(airportRepository, times(1)).save(airport);
        verify(mapper, times(1)).toDto(airport);
        verify(mapper, times(1)).dtoToAirport(airportDto);


    }
    @Test
    void replaceAirportWithError() {
        //  airplaneRepository.save(airplane);
        when(airportRepository.findById(airport.getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->airportService.replaceAirport(1L, airport));



    }

    @Test
    void deleteAirport() {

        doNothing().when(airportRepository).deleteById(anyLong());
        when(tripService.findTripByAirplane(1L)).thenReturn(true);
        when(airportRepository.findById(airport.getId())).thenReturn(Optional.ofNullable(airport));

        Optional<Airport>  airport2 = airportRepository.findById(1L);
        if (airport2.isPresent()) {
            if(tripService.findTripByAirplane(1L)){
                airportService.deleteAirportById(1L);
            }

        }
        verify(airportRepository, times(1)).deleteById(anyLong());
        verify(tripService, times(1)).findTripByAirplane(anyLong());

    }
        @Test
        void dummyTest() {
            assertThrows(NotObjectFound.class,() ->airportService.errorTripPresent());
        }

}