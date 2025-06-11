package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.dto.TicketCreateDto;
import com.gman97.cinemachain.dto.TicketReadDto;
import com.gman97.cinemachain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bay-ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseBody
    public TicketReadDto[] bayTicket(@RequestBody List<TicketCreateDto> ticketsToBay) {
        return ticketService.bayTickets(ticketsToBay).toArray(TicketReadDto[]::new);
    }
}
