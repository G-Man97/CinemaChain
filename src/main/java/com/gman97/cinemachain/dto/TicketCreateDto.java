package com.gman97.cinemachain.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;


@Value
@Builder
public class TicketCreateDto {

    String title;

    String duration;

    LocalDate date;

    LocalTime beginSession;

    String cinemaName;

    Integer hallNumber;

    Long seatId;

    Short row;

    Short seatNo;

    Double cost;

    Long filmSessionId;
}
