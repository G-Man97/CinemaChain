package com.gman97.cinemachain.validation;

import com.gman97.cinemachain.validation.impl.CheckSeanceDateAndTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = CheckSeanceDateAndTimeValidator.class)
public @interface CheckSeanceDateAndTime {

    String message() default "Указанное время не подходит для начала сеанса, т.к. в это время в этом кинозале уже идёт другой сеанс";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
