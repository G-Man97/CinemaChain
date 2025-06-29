package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.CinemaReadDto;
import com.gman97.cinemachain.entity.Cinema;
import com.gman97.cinemachain.mapper.CinemaReadMapper;
import com.gman97.cinemachain.repository.CinemaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {

    @InjectMocks
    private CinemaService cinemaService;
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private CinemaReadMapper cinemaReadMapper;

    @Test
    void whenFindAll_thenReturnCinemaReadDtoList() {
        var foundCinemas = List.of(new Cinema(), new Cinema());
        var cinemaReadDto = new CinemaReadDto(new Random().nextInt(100), "test");

        when(cinemaRepository.findAll()).thenReturn(foundCinemas);
        when(cinemaReadMapper.map(any(Cinema.class))).thenReturn(cinemaReadDto);

        var actual = cinemaService.findAll();

        assertThat(actual.size()).isEqualTo(foundCinemas.size());
        assertThat(actual.get(0)).isInstanceOf(CinemaReadDto.class);
        verify(cinemaRepository).findAll();
        verify(cinemaReadMapper, times(foundCinemas.size())).map(any(Cinema.class));
    }

    @Test
    void whenFindAllNothingFound_thenReturnEmptyList() {
        List<Cinema> foundCinemas = List.of();

        when(cinemaRepository.findAll()).thenReturn(foundCinemas);

        var actual = cinemaService.findAll();

        assertThat(actual.size()).isEqualTo(0);
        verify(cinemaRepository).findAll();
        verify(cinemaReadMapper, times(0)).map(any(Cinema.class));
    }
}