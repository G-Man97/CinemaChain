package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SeatsForFilmSessionReadDto {

    Long id;

    Short row;

    Short seatNo;

    @Enumerated(EnumType.STRING)
    Status status;

    Integer ticketCost;
}
