package com.gman97.cinemachain.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
public class FilmSessionReadDto {

    Long id;

    LocalDate date;

    LocalTime beginSession;

    CinemaHallReadDto cinemaHall;
}
