package com.gman97.cinemachain.integration.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gman97.cinemachain.dto.TicketCreateDto;
import com.gman97.cinemachain.dto.TicketReadDto;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "user@gmail.com", password = "test12345678", authorities = {"USER"})
class TicketControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void bayTicket() throws Exception {
        var createDto = TicketCreateDto.builder()
                        .title("Ono")
                        .duration("135")
                        .beginSession(LocalTime.of(10, 0))
                        .cinemaName("Mir")
                        .hallNumber(2)
                        .seatId(3L)
                        .row((short) 1)
                        .seatNo((short) 3)
                        .cost(500D)
                        .filmSessionId(1L)
                        .date(LocalDate.now().plusDays(1))
                .build();
        var resultDto = TicketReadDto.builder()
                .id(1L)
                .movieTitle(createDto.getTitle())
                .movieDuration(createDto.getDuration())
                .beginSession(createDto.getBeginSession())
                .cinemaName(createDto.getCinemaName())
                .hallNumber(createDto.getHallNumber())
                .row(createDto.getRow())
                .seatNo(createDto.getSeatNo())
                .cost(createDto.getCost())
                .date(createDto.getDate())
                .build();
        var content = List.of(createDto);
        var result = new TicketReadDto[] {resultDto};
        mockMvc.perform(MockMvcRequestBuilders.post("/bay-ticket")
                        .content(objectMapper.writeValueAsString(content))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().is2xxSuccessful(),
                content().json(objectMapper.writeValueAsString(result))
        );

    }
}