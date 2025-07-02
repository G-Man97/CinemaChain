package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.repository.SeatsRepository;
import com.gman97.cinemachain.service.FilmSessionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RequiredArgsConstructor
class FilmSessionServiceTest extends IntegrationTestBase {

    private final FilmSessionService filmSessionService;
    private final MovieRepository movieRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final SeatsRepository seatsRepository;
    private final JdbcTemplate jdbcTemplate;

    @Test
    @Sql(value = {"classpath:sql/data.sql"}, statements = {"TRUNCATE seats_for_film_sessions;"})
    void createFilmSession() {
        // Киносеанс для фильма "Легенда", который ещё не в прокате
        var dto = FilmSessionCreateDto.builder()
                .movieId(4)
                .date(LocalDate.now().plusDays(4))
                .beginSession(LocalTime.of(14, 25))
                .cinemaHallId(3) // Кинозал размера MIDDLE
                .ticketCost(480)
                .build();

        var sessionsQuantityBeforeCreateNewSession = filmSessionRepository.findAll().size();
        var seatsQuantityForMiddleHall = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM hall_seats WHERE middle_hall_seat_exists = true", Integer.class);
        var movieIsItInRent = movieRepository.findById(dto.getMovieId()).get().getIsItInRent();


        filmSessionService.createFilmSession(dto);

        assertThat(sessionsQuantityBeforeCreateNewSession + 1).isEqualTo(filmSessionRepository.findAll().size());
        assertThat(seatsRepository.findAll().size()).isEqualTo(seatsQuantityForMiddleHall);
        assertThat(movieIsItInRent).isNotEqualTo(movieRepository.findById(dto.getMovieId()).get().getIsItInRent());
    }
}