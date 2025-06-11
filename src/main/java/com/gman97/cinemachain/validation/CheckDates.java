package com.gman97.cinemachain.validation;

import com.gman97.cinemachain.validation.impl.CheckDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CheckDatesValidator.class})
public @interface CheckDates {

    String message() default """
            При добавлении нового фильма: Дата премьеры должна быть раньше даты окончания проката. Интервал между датами должен быть не менее 7-и дней
            При редактировании фильма в прокате: нельзя менять дату премьеры, дата конца проката не может быть в прошлом. Интервал между датами должен быть не менее 7-и дней
            При редактировании фильма, который ещё НЕ в прокате: даты премьеры и конца проката должны быть в будущем. Интервал между датами должен быть не менее 7-и дней
            """;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
