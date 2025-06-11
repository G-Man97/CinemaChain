package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.entity.HallSize;
import lombok.Value;

@Value
public class CinemaHallFindAllDto {

        Integer id;

        Integer number;

        HallSize size;
}
