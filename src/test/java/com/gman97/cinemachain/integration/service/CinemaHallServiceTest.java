package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.entity.Cinema;
import com.gman97.cinemachain.entity.CinemaHall;
import com.gman97.cinemachain.entity.HallSize;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.mapper.CinemaHallFindAllMapper;
import com.gman97.cinemachain.repository.CinemaHallRepository;
import com.gman97.cinemachain.repository.CinemaRepository;
import com.gman97.cinemachain.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RequiredArgsConstructor
class CinemaHallServiceTest extends IntegrationTestBase {

    private final CinemaHallService cinemaHallService;
    private final CinemaRepository cinemaRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaHallFindAllMapper cinemaHallFindAllMapper;

    @Test
    void findAllByCinemaId() {
        var cinema = cinemaRepository.save(new Cinema(null, "Rodina", new ArrayList<>(2)));
        var hall1 = cinemaHallRepository.save(new CinemaHall(null, 1, HallSize.SMALL, cinema));
        var hall2 = cinemaHallRepository.save(new CinemaHall(null, 2, HallSize.MIDDLE, cinema));

        var actual = cinemaHallService.findAllByCinemaId(cinema.getId());

        assertThat(actual).hasSize(2);
        assertThat(actual).contains(cinemaHallFindAllMapper.map(hall1), cinemaHallFindAllMapper.map(hall2));
    }
}