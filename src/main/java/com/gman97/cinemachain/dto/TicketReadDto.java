package com.gman97.cinemachain.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
public class TicketReadDto {

    Long id;

    String movieTitle;

    String movieDuration;

    LocalTime beginSession;

    LocalDate date;

    String cinemaName;

    Integer hallNumber;

    Short row;

    Short seatNo;

    Double cost;
}
