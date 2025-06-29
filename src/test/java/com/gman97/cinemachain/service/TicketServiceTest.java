package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.TicketCreateDto;
import com.gman97.cinemachain.dto.TicketReadDto;
import com.gman97.cinemachain.entity.Ticket;
import com.gman97.cinemachain.mapper.TicketCreateMapper;
import com.gman97.cinemachain.repository.SeatsRepository;
import com.gman97.cinemachain.repository.TicketRepository;
import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private SeatsRepository seatsRepository;
    @Mock
    private TicketCreateMapper ticketCreateMapper;

    @Test
    void bayTickets() {
        List<TicketCreateDto> dtos = List.of(TicketCreateDto.builder().seatId(10L).build(),
                                             TicketCreateDto.builder().seatId(15L).build());
        var ticket = new Ticket();

        when(seatsRepository.updateStatusForBoughtSeats(anyList())).thenReturn(dtos.size());
        when(ticketCreateMapper.map(any(TicketCreateDto.class))).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketCreateMapper.mapToReadDto(ticket)).thenReturn(TicketReadDto.builder().build());

        var actual = ticketService.bayTickets(dtos);

        assertThat(actual).hasSize(dtos.size());

        verify(seatsRepository).updateStatusForBoughtSeats(anyList());
        verify(ticketCreateMapper, times(dtos.size())).map(any(TicketCreateDto.class));
        verify(ticketRepository, times(dtos.size())).save(any(Ticket.class));
        verify(ticketCreateMapper, times(dtos.size())).mapToReadDto(ticket);
    }

    @Test
    void bayTicketsShouldThrowStaleObjectStateException_whenAnotherTransactionAlreadyModifiedRowsInDB() {
        List<TicketCreateDto> dtos = List.of(TicketCreateDto.builder().seatId(10L).build(),
                                             TicketCreateDto.builder().seatId(15L).build());

        when(seatsRepository.updateStatusForBoughtSeats(anyList())).thenReturn(dtos.size() - 1);

        assertThrows(StaleObjectStateException.class, () -> ticketService.bayTickets(dtos));

        verify(seatsRepository).updateStatusForBoughtSeats(anyList());
        verify(ticketCreateMapper, never()).map(any(TicketCreateDto.class));
        verify(ticketRepository, never()).save(any(Ticket.class));
        verify(ticketCreateMapper, never()).mapToReadDto(any(Ticket.class));
    }
}