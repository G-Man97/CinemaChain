package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.SeatsForFilmSessionReadDto;
import com.gman97.cinemachain.mapper.SeatsForFilmSessionReadMapper;
import com.gman97.cinemachain.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatsForFilmSessionService {

    private final SeatsRepository seatsRepository;
    private final SeatsForFilmSessionReadMapper seatsForFilmSessionReadMapper;

    public List<SeatsForFilmSessionReadDto> findByFilmSessionId(Long filmSessionId) {
        return seatsRepository.findAllByFilmSessionId(filmSessionId).stream()
                .map(seatsForFilmSessionReadMapper::map)
                .toList();
    }
}
