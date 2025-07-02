package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.entity.Cinema;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.mapper.CinemaReadMapper;
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
    private final CinemaReadMapper cinemaReadMapper;

    @Test
    void findAll() {
        // Скрипт data.sql накатил 2 кинотеатра
        var cinema1 = new Cinema(1, "Mir", new ArrayList<>(0));
        var cinema2 = new Cinema(2, "Galaxy", new ArrayList<>(0));

        var actual = cinemaService.findAll();

        assertThat(actual).hasSize(2);
        assertThat(actual).contains(cinemaReadMapper.map(cinema1), cinemaReadMapper.map(cinema2));
    }
}