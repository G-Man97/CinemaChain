package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.CinemaHallFindAllDto;
import com.gman97.cinemachain.mapper.CinemaHallFindAllMapper;
import com.gman97.cinemachain.repository.CinemaHallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaHallFindAllMapper cinemaHallFindAllMapper;

    public CinemaHallFindAllDto[] findByCinemaId(Integer id) {
        return cinemaHallRepository.findCinemaHallsByCinemaId(id).stream()
                .map(cinemaHallFindAllMapper::map)
                .toArray(CinemaHallFindAllDto[]::new);
    }
}
