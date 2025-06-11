package com.gman97.cinemachain.validation;

import com.gman97.cinemachain.validation.impl.PosterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PosterValidator.class})
public @interface PosterValidate {

    String message() default "Выбранный файл должен быть изображением, размер которого не превышает 5Мб! " +
            "Загрузите изображение заново, если при отправке формы на сервер в предыдущий раз были ошибки заполнения других полей";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
