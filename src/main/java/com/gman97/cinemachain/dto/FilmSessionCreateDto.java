package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.validation.CheckSeanceDateAndTime;
import com.gman97.cinemachain.validation.NotFromMidnightTo9am;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.gman97.cinemachain.util.ValidationStringUtils.FIELD_MUST_BE_NOT_EMPTY;

@Value
@Builder
@FieldNameConstants
@CheckSeanceDateAndTime
public class FilmSessionCreateDto {

    Integer movieId;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @NotFromMidnightTo9am
    LocalTime beginSession;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    Integer cinemaHallId;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @Future(message = "Дата сеанса должна быть в будущем!")
    LocalDate date;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @Min(value = 250, message = "Минимальная стоимость билета 250р.")
    @Max(value = 1500, message = "Максимальная стоимость билета 1500р.")
    Integer ticketCost;
}
