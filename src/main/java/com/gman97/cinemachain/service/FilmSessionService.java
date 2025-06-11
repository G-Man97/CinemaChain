package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.dto.SeanceInfoDto;
import com.gman97.cinemachain.entity.FilmSession;
import com.gman97.cinemachain.entity.Movie;
import com.gman97.cinemachain.mapper.FilmSessionCreateMapper;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final SeatsRepository seatsRepository;
    private final MovieRepository movieRepository;
    private final FilmSessionCreateMapper filmSessionCreateMapper;

    @Transactional
    public void createFilmSession(FilmSessionCreateDto filmSessionDto) {
        FilmSession filmSession = filmSessionRepository.save(filmSessionCreateMapper.map(filmSessionDto));
        Movie movie = filmSession.getMovie();

        if (movie.getIsItInRent().equals(Boolean.FALSE)) {
            movie.setIsItInRent(Boolean.TRUE);
            movieRepository.save(movie);
        }

        seatsRepository.createSeatsForFilmSession(filmSession.getId(),
                                                filmSession.getCinemaHall().getSize(),
                                                filmSessionDto.getTicketCost());
    }

    public SeanceInfoDto findSeanceInfoById(Long filmSessionId) {
        return filmSessionRepository.findSeanceInfoById(filmSessionId)
                .orElse(null);
    }
}
