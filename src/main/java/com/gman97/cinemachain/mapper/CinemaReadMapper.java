package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.CinemaReadDto;
import com.gman97.cinemachain.entity.Cinema;
import org.springframework.stereotype.Component;

@Component
public class CinemaReadMapper implements Mapper<Cinema, CinemaReadDto> {

    @Override
    public CinemaReadDto map(Cinema object) {
        return new CinemaReadDto(
                object.getId(),
                object.getName()
        );
    }
}
