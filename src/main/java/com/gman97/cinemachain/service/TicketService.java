package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.TicketCreateDto;
import com.gman97.cinemachain.dto.TicketReadDto;
import com.gman97.cinemachain.mapper.TicketCreateMapper;
import com.gman97.cinemachain.repository.SeatsRepository;
import com.gman97.cinemachain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
        var dtosToSeatIdsArray = dtos.stream()
                        .map(TicketCreateDto::getSeatId)
                        .toList();

        if(seatsRepository.updateStatusForBoughtSeats(dtosToSeatIdsArray) == dtos.size()) {
            List<TicketReadDto> tickets = new ArrayList<>();

            for (TicketCreateDto dto : dtos) {
                tickets.add(ticketCreateMapper.mapToReadDto(
                        ticketRepository.save(ticketCreateMapper.map(dto))
                ));
            }

            return tickets;
        }
        return null;
    }
}
