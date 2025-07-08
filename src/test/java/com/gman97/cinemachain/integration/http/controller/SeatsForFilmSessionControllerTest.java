package com.gman97.cinemachain.integration.http.controller;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "user@gmail.com", password = "test12345678", authorities = {"USER"})
class SeatsForFilmSessionControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findByFilmSessionId() throws Exception {
        mockMvc.perform(get("/seats/{filmSessionId}", 1))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("seats"),
                        model().attributeExists("seats", "seanceInfo")
                );
    }
}