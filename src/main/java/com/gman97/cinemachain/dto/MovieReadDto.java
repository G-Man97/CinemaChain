package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.entity.Pegi;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class MovieReadDto {

    Integer id;

    String title;

    LocalDate premiere;

    LocalDate rentEnd;

    String duration;

    Pegi pegi;

    String genre;

    String countries;

    String composer;

    String producer;

    String actors;

    String description;

    String poster;

    Boolean isItInRent;

    @Builder.Default
    Map<CinemaReadDto, List<FilmSessionReadDto>> filmSessionsByCinema = new HashMap<>();
}
