package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.entity.CinemaHall;
import com.gman97.cinemachain.entity.FilmSession;
import com.gman97.cinemachain.entity.Movie;
import com.gman97.cinemachain.repository.CinemaHallRepository;
import com.gman97.cinemachain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmSessionCreateMapper implements Mapper<FilmSessionCreateDto, FilmSession> {

    private final MovieRepository movieRepository;
    private final CinemaHallRepository cinemaHallRepository;

    @Override
    public FilmSession map(FilmSessionCreateDto object) {
        return new FilmSession(
                null,
                object.getDate(),
                object.getBeginSession(),
                getMovie(object.getMovieId()),
                getCinemaHall(object.getCinemaHallId())
        );
    }

    private CinemaHall getCinemaHall(Integer cinemaHallId) {
        return Optional.ofNullable(cinemaHallId)
                .flatMap(cinemaHallRepository::findById)
                .orElse(null);
    }

    private Movie getMovie(Integer movieId) {
        return Optional.ofNullable(movieId)
                .flatMap(movieRepository::findById)
                .orElse(null);
    }
}
