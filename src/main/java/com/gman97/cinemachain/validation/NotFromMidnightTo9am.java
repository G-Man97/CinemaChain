package com.gman97.cinemachain.validation;

import com.gman97.cinemachain.validation.impl.NotFromMidnightTo9amValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotFromMidnightTo9amValidator.class)
public @interface NotFromMidnightTo9am {

    String message() default "Время сеанса не должно быть в отрезке с полуночи до 9 утра (не рабочие часы)";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
