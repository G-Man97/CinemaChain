package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.service.SeatsForFilmSessionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@RequiredArgsConstructor
class SeatsForFilmSessionServiceTest extends IntegrationTestBase {

    private final SeatsForFilmSessionService seatsForFilmSessionService;
    private final JdbcTemplate jdbcTemplate;

    @Test
    void findByFilmSessionId() {
        var filmSessionInBigHallId = 3L;
        var seatsQuantityForBigHall = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM hall_seats WHERE big_hall_seat_exists = true", Integer.class);

        var actual = seatsForFilmSessionService.findByFilmSessionId(filmSessionInBigHallId);

        assertThat(actual.size()).isEqualTo(seatsQuantityForBigHall);
    }
}