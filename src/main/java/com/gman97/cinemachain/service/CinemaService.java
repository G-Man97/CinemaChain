package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.CinemaReadDto;
import com.gman97.cinemachain.mapper.CinemaReadMapper;
import com.gman97.cinemachain.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaReadMapper cinemaReadMapper;

    public List<CinemaReadDto> findAll() {
        return cinemaRepository.findAll().stream()
                .map(cinemaReadMapper::map)
                .toList();
    }
}
