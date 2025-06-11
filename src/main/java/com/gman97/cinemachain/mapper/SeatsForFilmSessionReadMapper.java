package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.SeatsForFilmSessionReadDto;
import com.gman97.cinemachain.entity.SeatsForFilmSession;
import org.springframework.stereotype.Component;

@Component
public class SeatsForFilmSessionReadMapper implements Mapper<SeatsForFilmSession, SeatsForFilmSessionReadDto> {

    @Override
    public SeatsForFilmSessionReadDto map(SeatsForFilmSession object) {
        return new SeatsForFilmSessionReadDto(
                object.getId(),
                object.getRow(),
                object.getSeatNo(),
                object.getStatus(),
                object.getTicketCost()
        );
    }
}
