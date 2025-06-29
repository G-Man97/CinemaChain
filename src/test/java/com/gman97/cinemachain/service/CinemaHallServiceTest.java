package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.CinemaHallFindAllDto;
import com.gman97.cinemachain.entity.CinemaHall;
import com.gman97.cinemachain.entity.HallSize;
import com.gman97.cinemachain.mapper.CinemaHallFindAllMapper;
import com.gman97.cinemachain.repository.CinemaHallRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaHallServiceTest {

    @InjectMocks
    private CinemaHallService cinemaHallService;
    @Mock
    private CinemaHallRepository cinemaHallRepository;
    @Mock
    private CinemaHallFindAllMapper cinemaHallFindAllMapper;

    @Test
    void whenFindAllByCinemaIdFoundCinemaHalls_thenReturnArrayOfCinemaHallFindAllDto() {
        var gen = new Random();
        List<CinemaHall> foundCinemaHalls = List.of(new CinemaHall(), new CinemaHall());
        var cinemaHallFindAllDto = new CinemaHallFindAllDto(gen.nextInt(100), gen.nextInt(6), HallSize.MIDDLE);

        when(cinemaHallRepository.findAllByCinemaId(anyInt())).thenReturn(foundCinemaHalls);
        when(cinemaHallFindAllMapper.map(any(CinemaHall.class))).thenReturn(cinemaHallFindAllDto);

        var actual = cinemaHallService.findAllByCinemaId(1);

        assertThat(actual.length).isEqualTo(foundCinemaHalls.size());
        assertThat(actual[0]).isInstanceOf(CinemaHallFindAllDto.class);
        verify(cinemaHallRepository).findAllByCinemaId(anyInt());
        verify(cinemaHallFindAllMapper, times(foundCinemaHalls.size())).map(any(CinemaHall.class));
    }

    @Test
    void whenFindAllByCinemaIdNothingFound_thenReturnEmptyArray() {
        List<CinemaHall> foundCinemaHalls = List.of();

        when(cinemaHallRepository.findAllByCinemaId(anyInt())).thenReturn(foundCinemaHalls);

        var actual = cinemaHallService.findAllByCinemaId(2);

        assertThat(actual.length).isEqualTo(0);
        verify(cinemaHallRepository).findAllByCinemaId(anyInt());
        verify(cinemaHallFindAllMapper, times(0)).map(any(CinemaHall.class));
    }
}