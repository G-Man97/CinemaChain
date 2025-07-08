package com.gman97.cinemachain.integration.http.controller;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "user@gmail.com", password = "test12345678", authorities = {"USER"})
class MovieControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAllInRent() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("movies"),
                        model().attributeExists("movies"),
                        model().attribute("movies", hasSize(2))
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/movies/{id}", 1))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("movie"),
                        model().attributeExists("movie")
                );
    }

    @Test
    void findAllSoon() throws Exception {
        mockMvc.perform(get("/movies/soon"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("soon"),
                        model().attributeExists("movies"),
                        model().attribute("movies", hasSize(2))
                );
    }

    @Test
    void findByIdSoon() throws Exception {
        mockMvc.perform(get("/movies/soon/{id}", 3))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("movie"),
                        model().attributeExists("movie")
                );

    }
}