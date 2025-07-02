package com.gman97.cinemachain.integration.repository;

import com.gman97.cinemachain.entity.FilmSession;
import com.gman97.cinemachain.entity.Status;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.CinemaHallRepository;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@RequiredArgsConstructor
class SeatsRepositoryTest extends IntegrationTestBase {

    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final SeatsRepository seatsRepository;

    @Test
    @DisplayName("createSeatsForFilmSession_and_findAllByFilmSessionId")
    void givenFilmSession_whenCreateSeatsForFilmSession_thenReturnCountOfInsertedRows() {

        final int MADAGASCAR_MOVIE_ID = 3;
        final int NUMBER_1_SMALL_MIR_CINEMA_HALLS_ID = 4;

        FilmSession filmSession =
                new FilmSession(null,
                        LocalDate.now().plusDays(1),
                        LocalTime.of(16, 30),
                        movieRepository.findById(MADAGASCAR_MOVIE_ID).get(),
                        cinemaHallRepository.findById(NUMBER_1_SMALL_MIR_CINEMA_HALLS_ID).get()
                );
        filmSessionRepository.save(filmSession);

        final int SEATS_QUANTITY_FOR_SMALL_HALL = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM hall_seats WHERE small_hall_seat_exists = true", Integer.class);

        seatsRepository.createSeatsForFilmSession(filmSession.getId(),
                                                                    filmSession.getCinemaHall().getSize(),
                                                                    500);

        int result = seatsRepository.findAllByFilmSessionId(filmSession.getId()).size();

        assertThat(result).isEqualTo(SEATS_QUANTITY_FOR_SMALL_HALL);
    }

    @Test
    @DisplayName("updateStatusForBoughtSeats")
    void givenSeatIdList_whenUpdateStatusForBoughtSeats_thenReturnQuantityOfUpdatedRows() {

        List<Long> nonBoughtSeatIds = List.of(1L, 2L, 3L);

        int result = seatsRepository.updateStatusForBoughtSeats(nonBoughtSeatIds);

        assertThat(result).isEqualTo(nonBoughtSeatIds.size());
        seatsRepository.findAllById(nonBoughtSeatIds)
                .forEach(seat ->
                        assertThat(seat.getStatus()).isEqualTo(Status.TAKEN)
                );
    }
}