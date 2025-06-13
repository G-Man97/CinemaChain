package com.gman97.cinemachain.dto;


import java.time.LocalDate;
import java.time.LocalTime;

public interface SeanceInfoDto {

    LocalDate getDate();

    Integer getDuration();

    LocalTime getBeginSession();

    Integer getHallNumber();

    String getMovieTitle();

    String getCinemaName();

    Long getFilmSessionId();
}
