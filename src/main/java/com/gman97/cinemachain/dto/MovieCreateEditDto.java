package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.entity.Pegi;
import com.gman97.cinemachain.validation.CheckDates;
import com.gman97.cinemachain.validation.PosterValidate;
import com.gman97.cinemachain.validation.group.CreateAction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static com.gman97.cinemachain.util.ValidationStringUtils.FIELD_MUST_BE_NOT_EMPTY;

@Value
@Builder
@CheckDates
@FieldNameConstants
public class MovieCreateEditDto {

    Integer id;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    @Size(min = 1, max = 32, message = "От 1-го до 32-х символов!")
    String title;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @Future(message = "Дата премьеры должна быть в будущем!", groups = CreateAction.class)
    LocalDate premiere;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @Future(message = "Дата конца проката должна быть в будущем!", groups = CreateAction.class)
    LocalDate rentEnd;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @Min(value = 40, message = "Минимальная длительность фильма для показа в кинотеатре составляет 40 минут")
    @Max(value = 250, message = "Максимальная длительность фильма 250 минут")
    Integer duration;

    @NotNull(message = FIELD_MUST_BE_NOT_EMPTY)
    @Enumerated(EnumType.STRING)
    Pegi pegi;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    String genre;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    String countries;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    String composer;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    String producer;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    String actors;

    @NotBlank(message = FIELD_MUST_BE_NOT_EMPTY)
    String description;

    Boolean isItInRent;

    @PosterValidate(groups = CreateAction.class)
    MultipartFile poster;

}
