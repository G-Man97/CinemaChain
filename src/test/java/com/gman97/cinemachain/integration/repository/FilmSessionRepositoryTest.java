package com.gman97.cinemachain.integration.repository;

import com.gman97.cinemachain.dto.SeanceInfoDto;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RequiredArgsConstructor
class FilmSessionRepositoryTest extends IntegrationTestBase {

    private final FilmSessionRepository filmSessionRepository;

    @Test
    @DisplayName("findSeanceInfoById")
    void givenFilmSessionId_whenFindSeanceInfoById_thenReturnSeanceInfoDto() {

        final long EXISTING_FILM_SESSIONS_ID = 3L;

        SeanceInfoDto seanceInfoFromDB = filmSessionRepository.findSeanceInfoById(EXISTING_FILM_SESSIONS_ID).get();

        assertThat(seanceInfoFromDB).isNotNull();
    }

    @Test
    @DisplayName("findAllByCinemaHallIdAndInterval")
    void givenFilmSessionList_whenFindAllByCinemaHallIdAndInterval_thenReturnFilmSessionList() {

        var filmSessionList = filmSessionRepository.findAllByCinemaHallIdAndInterval(1,
                                                                                    LocalDate.now().plusDays(1L),
                                                                                    LocalTime.of(9, 0),
                                                                                    LocalTime.of(13, 0));

        assertThat(filmSessionList).isNotNull();
        assertThat(filmSessionList.size()).isEqualTo(2);
    }
}