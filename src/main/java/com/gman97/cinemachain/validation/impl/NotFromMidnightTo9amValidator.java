package com.gman97.cinemachain.validation.impl;

import com.gman97.cinemachain.validation.NotFromMidnightTo9am;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.util.Optional;

public class NotFromMidnightTo9amValidator implements ConstraintValidator<NotFromMidnightTo9am, LocalTime> {

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(it -> it.isAfter(LocalTime.of(9, 0)))
                .orElse(true);
    }
}
