package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.CinemaHallFindAllDto;
import com.gman97.cinemachain.entity.CinemaHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CinemaHallFindAllMapper implements Mapper<CinemaHall, CinemaHallFindAllDto> {

    @Override
    public CinemaHallFindAllDto map(CinemaHall object) {
        return new CinemaHallFindAllDto(
                object.getId(),
                object.getNumber(),
                object.getSize()
        );
    }
}
