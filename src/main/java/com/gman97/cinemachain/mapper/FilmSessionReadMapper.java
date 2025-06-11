package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.FilmSessionReadDto;
import com.gman97.cinemachain.entity.FilmSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmSessionReadMapper implements Mapper<FilmSession, FilmSessionReadDto> {

    private final CinemaHallReadMapper cinemaHallReadMapper;

    @Override
    public FilmSessionReadDto map(FilmSession object) {
        return FilmSessionReadDto.builder()
                .id(object.getId())
                .date(object.getDate())
                .beginSession(object.getBeginSession())
                .cinemaHall(cinemaHallReadMapper.map(object.getCinemaHall()))
                .build();
    }
}
