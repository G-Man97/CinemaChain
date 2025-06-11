package com.gman97.cinemachain.validation.impl;

import com.gman97.cinemachain.validation.PosterValidate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class PosterValidator implements ConstraintValidator<PosterValidate, MultipartFile> {

    private static final long MAX_FILE_SIZE = 5_242_880L;

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return !value.isEmpty()
                && value.getContentType().startsWith("image/")
                && value.getSize() <= MAX_FILE_SIZE;
    }
}
