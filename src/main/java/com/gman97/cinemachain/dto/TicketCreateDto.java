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

    public TicketCreateDto(String title, String duration, LocalDate date, LocalTime beginSession, String cinemaName, Integer hallNumber, Long seatId, Short row, Short seatNo, Double cost, Long filmSessionId) {
        this.title = title;
        this.duration = duration;
        this.date = date;
        this.beginSession = beginSession;
        this.cinemaName = cinemaName;
        this.hallNumber = hallNumber;
        this.seatId = seatId;
        this.row = row;
        this.seatNo = seatNo;
        this.cost = cost;
        this.filmSessionId = filmSessionId;
    }
}
