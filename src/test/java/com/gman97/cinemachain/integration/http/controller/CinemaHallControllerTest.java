package com.gman97.cinemachain.integration.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gman97.cinemachain.dto.CinemaHallFindAllDto;
import com.gman97.cinemachain.entity.HallSize;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "admin@gmail.com", password = "test12345678", authorities = {"ADMIN"})
class CinemaHallControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void findByCinemaId() throws Exception {
        var cinemaHall1 = new CinemaHallFindAllDto(1, 2, HallSize.MIDDLE);
        var cinemaHall2 = new CinemaHallFindAllDto(4, 1, HallSize.SMALL);

        var expected = new CinemaHallFindAllDto[] {cinemaHall1, cinemaHall2};

        mockMvc.perform(get("/cinema-halls/by-cinema-id/{id}", 1))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json(objectMapper.writeValueAsString(expected))
                );
    }
}