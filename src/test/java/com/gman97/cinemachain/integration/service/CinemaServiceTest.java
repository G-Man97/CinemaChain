package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.entity.Cinema;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.CinemaRepository;
import com.gman97.cinemachain.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@RequiredArgsConstructor
class CinemaServiceTest extends IntegrationTestBase {

    private final CinemaService cinemaService;
    private final CinemaRepository cinemaRepository;

    @Test
    void findAll() {

        var cinema1 = new Cinema(null, "Test", new ArrayList<>());
        var cinema2 = new Cinema(null, "Test2", new ArrayList<>());
        cinemaRepository.save(cinema1);
        cinemaRepository.save(cinema2);

        var list = cinemaService.findAll();

        assertThat(list).isNotEmpty();
        assertThat(list).hasSize(2);
    }
}