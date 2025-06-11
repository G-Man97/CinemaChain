package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.entity.HallSize;
import lombok.Value;

@Value
public class CinemaHallReadDto {

    Integer id;

    Integer number;

    HallSize size;

    CinemaReadDto cinema;
}
