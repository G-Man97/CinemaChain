package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.SeatsForFilmSessionReadDto;
import com.gman97.cinemachain.mapper.SeatsForFilmSessionReadMapper;
import com.gman97.cinemachain.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatsForFilmSessionService {

    private final SeatsRepository seatsRepository;
    private final SeatsForFilmSessionReadMapper seatsForFilmSessionReadMapper;

    public List<SeatsForFilmSessionReadDto> findByFilmSessionId(Long filmSessionId) {
        return seatsRepository.findByFilmSessionId(filmSessionId).stream()
                .map(seatsForFilmSessionReadMapper::map)
                .toList();
    }

//    public Map<String, String> findByFilmSessionId(Long filmSessionId) {
//        return seatsRepository.findByFilmSessionId(filmSessionId).stream()
//                .map(seatsForFilmSessionReadMapper::map)
//                .collect(Collectors.toMap(
//                        dto -> dto.getRow() + "," + dto.getSeatNo(),
//                        dto -> dto.getStatus().toString() + "," + dto.getTicketCost()));
//    }
}
