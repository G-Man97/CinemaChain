package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.SeatsForFilmSessionReadDto;
import com.gman97.cinemachain.entity.SeatsForFilmSession;
import com.gman97.cinemachain.mapper.SeatsForFilmSessionReadMapper;
import com.gman97.cinemachain.repository.SeatsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatsForFilmSessionServiceTest {

    @InjectMocks
    private SeatsForFilmSessionService seatsForFilmSessionService;
    @Mock
    private SeatsRepository seatsRepository;
    @Mock
    private SeatsForFilmSessionReadMapper seatsForFilmSessionReadMapper;

    @Test
    void findByFilmSessionId() {
        Long filmSessionId = 1L;
        var seats = List.of(new SeatsForFilmSession(), new SeatsForFilmSession(), new SeatsForFilmSession());

        when(seatsRepository.findAllByFilmSessionId(filmSessionId)).thenReturn(seats);
        when(seatsForFilmSessionReadMapper.map(any(SeatsForFilmSession.class)))
                .thenReturn(SeatsForFilmSessionReadDto.builder().build());

        var actual = seatsForFilmSessionService.findByFilmSessionId(filmSessionId);

        assertThat(actual).hasSize(seats.size());
        verify(seatsRepository).findAllByFilmSessionId(filmSessionId);
        verify(seatsForFilmSessionReadMapper, times(seats.size())).map(any(SeatsForFilmSession.class));
    }
}