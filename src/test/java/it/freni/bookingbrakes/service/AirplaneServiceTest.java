package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.airplane.AirplaneDto;
import it.freni.bookingbrakes.domain.Airplane;
import it.freni.bookingbrakes.domain.AirplaneType;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.AirplaneMapper;
import it.freni.bookingbrakes.mapper.AirplaneMapperImpl;
import it.freni.bookingbrakes.repository.AirplaneRepository;
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
class AirplaneServiceTest {

    @Mock
    private AirplaneRepository airplaneRepository;
    @Mock
    private AirplaneMapper mapper = new AirplaneMapperImpl();
    @Mock
    private TripService  tripService;

    @Autowired
    @InjectMocks
    private AirplaneService airplaneService;
    private Airplane airplane;
    private List<Airplane> airplaneList;
    private AirplaneDto airplaneDto;

    @BeforeEach
    public void setUp() {

        airplane = new Airplane();
        airplane.setId(1L);
        airplane.setAirplaneType(AirplaneType.AIRBUS);
        airplane.setAvaibleFlight(true);
        airplane.setName("StefanoFreni");
        airplane.setNumberSeats(150);
        airplaneList = new ArrayList<>();
        airplaneList.add(airplane);

        airplaneDto = new AirplaneDto();
        airplaneDto.setId(1L);
        airplaneDto.setAirplaneType(AirplaneType.AIRBUS);
        airplaneDto.setAvaibleFlight(true);
        airplaneDto.setName("StefanoFreni");
        airplaneDto.setNumberSeats(150);

    }

    @Test
    void getAirplane() {

        when(airplaneRepository.findById(1L)).thenReturn(Optional.ofNullable(airplane));
        assertThat(airplaneService.findById(airplane.getId())).get().isEqualTo(airplane);
        verify(airplaneRepository, times(1)).findById(any());
    }
    @Test
    void getAirplaneWithoutOptional() {

        when(airplaneRepository.findById(1L)).thenReturn(Optional.ofNullable(airplane));
        when(airplaneRepository.findById(0L)).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() -> airplaneService.findByIdWithoutOptional(0L));
        assertThat(airplaneService.findByIdWithoutOptional(airplane.getId())).isEqualTo(airplane);
        verify(airplaneRepository, times(2)).findById(any());
    }
    @Test
    void getAllAirplane() {
        airplaneRepository.save(airplane);

        when(airplaneRepository.findAll()).thenReturn(airplaneList);
        List<Airplane> airplanes = airplaneRepository.findAll();

        assertEquals(airplanes,airplaneList);
        verify(airplaneRepository, times(1)).save(any());
        verify(airplaneRepository, times(1)).findAll();

    }

    @Test
    void saveAirplane() {
        when(airplaneRepository.save(airplane)).thenReturn(airplane);
        Airplane airplane2= airplaneService.saveAirplane(airplane);
        assertEquals(airplane,airplane2);
        verify(airplaneRepository, times(1)).save(airplane);

    }

    @Test
    void replaceAirplane() {
      //  airplaneRepository.save(airplane);
        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.ofNullable(airplane));
        when(airplaneRepository.save(airplane)).thenReturn(airplane);
        when(mapper.toDto(airplane)).thenReturn(airplaneDto);
        when(mapper.dtoToAirplane(airplaneDto)).thenReturn(airplane);

        airplaneDto= airplaneService.replaceAirplane(1L, airplane);
        Airplane airplane2 = mapper.dtoToAirplane(airplaneDto);
        assertEquals(airplane,airplane2);
        verify(airplaneRepository, times(1)).save(airplane);
        verify(mapper, times(1)).toDto(airplane);
        verify(mapper, times(1)).dtoToAirplane(airplaneDto);


    }

    @Test
    void deleteAirplane() {

        doNothing().when(airplaneRepository).deleteById(anyLong());
        when(tripService.findTripByAirplane(1L)).thenReturn(true);
        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.ofNullable(airplane));

        Optional<Airplane>  airplane2 = airplaneRepository.findById(1L);
        if (airplane2.isPresent()) {
            if(tripService.findTripByAirplane(1L)){
                airplaneService.deleteAirplaneById(1L);
            }

        }
        verify(airplaneRepository, times(1)).deleteById(anyLong());
        verify(tripService, times(1)).findTripByAirplane(anyLong());

    }

}
