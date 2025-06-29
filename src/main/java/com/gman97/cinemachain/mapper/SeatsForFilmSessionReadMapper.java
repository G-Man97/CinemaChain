package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.SeatsForFilmSessionReadDto;
import com.gman97.cinemachain.entity.SeatsForFilmSession;
import org.springframework.stereotype.Component;

@Component
public class SeatsForFilmSessionReadMapper implements Mapper<SeatsForFilmSession, SeatsForFilmSessionReadDto> {

    @Override
    public SeatsForFilmSessionReadDto map(SeatsForFilmSession object) {
        return SeatsForFilmSessionReadDto.builder()
                .id(object.getId())
                .row(object.getRow())
                .seatNo(object.getSeatNo())
                .status(object.getStatus())
                .ticketCost(object.getTicketCost())
                .build();
    }
}
