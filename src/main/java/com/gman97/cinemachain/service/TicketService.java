package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.TicketCreateDto;
import com.gman97.cinemachain.dto.TicketReadDto;
import com.gman97.cinemachain.entity.SeatsForFilmSession;
import com.gman97.cinemachain.mapper.TicketCreateMapper;
import com.gman97.cinemachain.repository.SeatsRepository;
import com.gman97.cinemachain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final SeatsRepository seatsRepository;
    private final TicketCreateMapper ticketCreateMapper;

    @Transactional
    public List<TicketReadDto> bayTickets(List<TicketCreateDto> dtos) {
        var dtosToSeatIdList = dtos.stream()
                        .map(TicketCreateDto::getSeatId)
                        .toList();

        if(seatsRepository.updateStatusForBoughtSeats(dtosToSeatIdList) != dtos.size()) {
            throw new StaleObjectStateException(SeatsForFilmSession.class.getName(), dtosToSeatIdList.toString());
        }

        List<TicketReadDto> tickets = new ArrayList<>(5);

        for (TicketCreateDto dto : dtos) {
            tickets.add(ticketCreateMapper.mapToReadDto(
                    ticketRepository.save(ticketCreateMapper.map(dto))
            ));
        }

        return tickets;
    }
}
