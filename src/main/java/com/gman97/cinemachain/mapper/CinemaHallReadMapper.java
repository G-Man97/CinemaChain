package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.CinemaHallReadDto;
import com.gman97.cinemachain.entity.CinemaHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CinemaHallReadMapper implements Mapper<CinemaHall, CinemaHallReadDto> {

    private final CinemaReadMapper cinemaReadMapper;

    @Override
    public CinemaHallReadDto map(CinemaHall object) {
        return new CinemaHallReadDto(
                object.getId(),
                object.getNumber(),
                object.getSize(),
                cinemaReadMapper.map(object.getCinema())
        );
    }
}
