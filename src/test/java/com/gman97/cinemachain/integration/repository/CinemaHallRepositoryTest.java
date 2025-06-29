package com.gman97.cinemachain.integration.repository;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.CinemaHallRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RequiredArgsConstructor
class CinemaHallRepositoryTest extends IntegrationTestBase {

    private final CinemaHallRepository cinemaHallRepository;

    @Test
    @DisplayName("findByCinemaId")
    void givenCinemaId_whenFindByCinemaId_thenReturnCinemaHallList() {

        final int EXISTING_CINEMAS_ID = 1;

        var cinemaHallList = cinemaHallRepository.findAllByCinemaId(EXISTING_CINEMAS_ID);

        assertThat(cinemaHallList).isNotNull();
        assertThat(cinemaHallList.size()).isEqualTo(2);

    }
}