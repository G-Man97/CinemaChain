package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.entity.HallSize;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SeanceInfoDto {

    LocalDate getDate();

    String getDuration();

    LocalTime getBeginSession();

    Integer getHallNumber();

    String getMovieTitle();

    String getCinemaName();

    Long getFilmSessionId();
}
