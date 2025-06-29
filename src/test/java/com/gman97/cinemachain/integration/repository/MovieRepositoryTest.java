package com.gman97.cinemachain.integration.repository;

import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RequiredArgsConstructor
class MovieRepositoryTest extends IntegrationTestBase {

    private final MovieRepository movieRepository;

    @Test
    @DisplayName("findByIdWithFetch")
    void givenMovieId_whenFindByIdWithFetch_thenReturnMovieObject() {

        final int EXISTING_MOVIES_ID = 2;

        var movieFromDB = movieRepository.findByIdWithFetch(EXISTING_MOVIES_ID).get();

        assertThat(movieFromDB).isNotNull();
        assertThat(movieFromDB.getTitle()).isEqualTo("Mgla");
        assertThat(movieFromDB.getFilmSessions()).isNotEmpty();
        assertThat(movieFromDB.getFilmSessions().get(0).getCinemaHall()).isNotNull();
        assertThat(movieFromDB.getFilmSessions().get(0).getCinemaHall().getCinema()).isNotNull();
    }

    @Test
    @DisplayName("findAllByIsItInRent")
    void givenFlagTrueAndMovieList_whenFindAllByIsItInRent_thenReturnMovieList() {
        final boolean IS_IT_IN_RENT = true;
        final int ONO_MOVIE_ID = 1;
        final int MGLA_MOVIE_ID = 2;

        var ono = movieRepository.findById(ONO_MOVIE_ID).get();
        var mgla = movieRepository.findById(MGLA_MOVIE_ID).get();

        var movieList = movieRepository.findAllByIsItInRent(IS_IT_IN_RENT);

        assertThat(movieList).isNotNull();
        assertThat(movieList.size()).isEqualTo(2);
        assertThat(movieList).containsOnly(ono, mgla);
    }
}