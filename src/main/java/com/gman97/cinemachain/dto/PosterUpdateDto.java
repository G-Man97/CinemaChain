package com.gman97.cinemachain.dto;

import com.gman97.cinemachain.validation.PosterValidate;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class PosterUpdateDto {

    Integer movieId;

    @PosterValidate
    MultipartFile poster;
}
