package com.gman97.cinemachain.integration.http.controller;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
class RegistrationControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void registerPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/registration"),
                        model().attributeExists("user")
                );
    }

    @Test
    void registerWhenDtoIsValid() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "testUser@gtest.com")
                .param("rowPassword", "qwerty12345")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/login")
        );
    }

    @Test
    void registerWhenDtoIsInvalid() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "testUserNotEmail")
                .param("rowPassword", "123")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/register"),
                flash().attributeExists("user", "errors")
        );
    }

    @Test
    void registerWhenSuchUserAlreadyExists() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "someUser@test.com")
                .param("rowPassword", "1234567890")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/register"),
                flash().attributeExists("user", "errors"),
                flash().attribute("errors", equalTo(List.of("Пользователь с таким именем уже существует!")))
        );
    }
}