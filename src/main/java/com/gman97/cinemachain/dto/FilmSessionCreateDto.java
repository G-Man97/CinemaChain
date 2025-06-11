package com.gman97.cinemachain.dto;

import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class FilmSessionCreateDto {

    LocalTime beginSession;

    Integer movieId;

    Integer cinemaHallId;

    LocalDate date;

    Integer ticketCost;
}
