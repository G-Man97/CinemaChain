package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.MovieFindAllDto;
import com.gman97.cinemachain.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieFindAllMapper implements Mapper<Movie, MovieFindAllDto> {

    @Override
    public MovieFindAllDto map(Movie object) {
        return new MovieFindAllDto(
                object.getId(),
                object.getTitle(),
                object.getPegi().getValue(),
                object.getPoster()
        );
    }
}
