package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.*;
import com.gman97.cinemachain.entity.Cinema;
import com.gman97.cinemachain.entity.FilmSession;
import com.gman97.cinemachain.entity.Movie;
import com.gman97.cinemachain.entity.Ticket;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketCreateMapper implements Mapper<TicketCreateDto, Ticket> {

    private final FilmSessionRepository filmSessionRepository;

    @Override
    public Ticket map(TicketCreateDto object) {
        return new Ticket(
                null,
                object.getTitle(),
                object.getDuration(),
                object.getDate(),
                object.getBeginSession(),
                object.getCinemaName(),
                object.getHallNumber(),
                object.getRow(),
                object.getSeatNo(),
                object.getCost(),
                findFilmSessionById(object.getFilmSessionId())
            );
    }

    public TicketReadDto mapToReadDto(Ticket ticket) {
        return TicketReadDto.builder()
                .id(ticket.getId())
                .movieTitle(ticket.getMovieTitle())
                .date(ticket.getDate())
                .cinemaName(ticket.getCinemaName())
                .beginSession(ticket.getBeginSession())
                .movieDuration(ticket.getMovieDuration())
                .hallNumber(ticket.getHallNumber())
                .row(ticket.getRow())
                .seatNo(ticket.getSeatNo())
                .cost(ticket.getCost())
                .build();
    }

    private FilmSession findFilmSessionById(Long id) {
        return filmSessionRepository.findById(id)
                .orElse(null);
    }
}
