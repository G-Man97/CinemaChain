package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.dto.SeanceInfoDto;
import com.gman97.cinemachain.entity.*;
import com.gman97.cinemachain.mapper.FilmSessionCreateMapper;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.repository.SeatsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmSessionServiceTest {

    @InjectMocks
    private FilmSessionService filmSessionService;
    @Mock
    private FilmSessionRepository filmSessionRepository;
    @Mock
    private SeatsRepository seatsRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private FilmSessionCreateMapper filmSessionCreateMapper;

    @Test
    void createFilmSession() {
        var dto = FilmSessionCreateDto.builder().ticketCost(500).build();
        var filmSession = new FilmSession();
        var movie = new Movie();

        movie.setIsItInRent(Boolean.FALSE);
        filmSession.setMovie(movie);
        filmSession.setId(10L);
        filmSession.setCinemaHall(new CinemaHall(2, 4, HallSize.SMALL, new Cinema()));

        when(filmSessionCreateMapper.map(dto)).thenReturn(filmSession);
        when(filmSessionRepository.save(filmSession)).thenReturn(filmSession);
        when(movieRepository.save(movie)).thenReturn(movie);
        doNothing().when(seatsRepository).createSeatsForFilmSession(filmSession.getId(),
                                                                    filmSession.getCinemaHall().getSize(),
                                                                    dto.getTicketCost());

        filmSessionService.createFilmSession(dto);

        verify(filmSessionCreateMapper).map(dto);
        verify(filmSessionRepository).save(filmSession);
        verify(movieRepository).save(movie);
        verify(seatsRepository).createSeatsForFilmSession(filmSession.getId(),
                                                            filmSession.getCinemaHall().getSize(),
                                                            dto.getTicketCost());

        movie.setIsItInRent(Boolean.TRUE);
        clearInvocations(filmSessionCreateMapper, filmSessionRepository, movieRepository, seatsRepository);

        filmSessionService.createFilmSession(dto);

        verify(filmSessionCreateMapper).map(dto);
        verify(filmSessionRepository).save(filmSession);
        verify(movieRepository, never()).save(movie);
        verify(seatsRepository).createSeatsForFilmSession(filmSession.getId(),
                                                            filmSession.getCinemaHall().getSize(),
                                                            dto.getTicketCost());
    }
}