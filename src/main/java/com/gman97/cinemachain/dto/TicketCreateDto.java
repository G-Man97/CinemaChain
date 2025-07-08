package com.gman97.cinemachain.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.time.LocalTime;


@Value
@Builder
@FieldNameConstants
public class TicketCreateDto {

    String title;

    //TODO Изменить тип duration со String на Integer и тип cost с Double на Integer
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
