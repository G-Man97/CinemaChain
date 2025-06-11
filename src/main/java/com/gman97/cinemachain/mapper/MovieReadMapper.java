package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.CinemaReadDto;
import com.gman97.cinemachain.dto.FilmSessionReadDto;
import com.gman97.cinemachain.dto.MovieReadDto;
import com.gman97.cinemachain.entity.Cinema;
import com.gman97.cinemachain.entity.FilmSession;
import com.gman97.cinemachain.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieReadMapper implements Mapper<Movie, MovieReadDto> {

    private final FilmSessionReadMapper filmSessionReadMapper;
    private final CinemaReadMapper cinemaReadMapper;

    @Override
    public MovieReadDto map(Movie object) {
        return copy(object)
                .filmSessionsByCinema(object.getIsItInRent().equals(Boolean.FALSE)
                        ? Collections.emptyMap()
                        : sessionsListToMap(object.getFilmSessions()))
                .build();

    }

    public MovieReadDto mapWithoutSessions(Movie object) {
        return copy(object)
                .filmSessionsByCinema(Collections.emptyMap())
                .build();
    }

    private MovieReadDto.MovieReadDtoBuilder copy(Movie object) {
        return MovieReadDto.builder()
                .id(object.getId())
                .title(object.getTitle())
                .duration(object.getDuration())
                .premiere(object.getPremiere())
                .rentEnd(object.getRentEnd())
                .pegi(object.getPegi())
                .genre(object.getGenre())
                .countries(object.getCountries())
                .producer(object.getProducer())
                .composer(object.getComposer())
                .actors(object.getActors())
                .description(object.getDescription())
                .poster(object.getPoster())
                .isItInRent(object.getIsItInRent());
    }


    private Map<CinemaReadDto, List<FilmSessionReadDto>> sessionsListToMap(List<FilmSession> foundSessions) {
        if (!foundSessions.isEmpty()) {
            Map<CinemaReadDto, List<FilmSessionReadDto>> sessionsByCinema = new HashMap<>();
            Set<Cinema> cinemasFromSessions = foundSessions.stream()
                    .map(it -> it.getCinemaHall().getCinema())
                    .collect(Collectors.toSet());

            for (Cinema cinema : cinemasFromSessions) {
                List<FilmSession> sessionsFromOneCinema = new ArrayList<>();

                for (FilmSession session : foundSessions) {
                    if (session.getCinemaHall().getCinema() == cinema) {
                        sessionsFromOneCinema.add(session);
                    }
                }

                sessionsFromOneCinema.sort(Comparator.comparing(FilmSession::getBeginSession));

                sessionsByCinema.put(
                        cinemaReadMapper.map(cinema),
                        sessionsFromOneCinema.stream().map(filmSessionReadMapper::map).toList()
                );
            }
            return sessionsByCinema;
        }

        return Collections.emptyMap();
    }
}
