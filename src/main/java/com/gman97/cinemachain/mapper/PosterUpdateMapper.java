package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.PosterUpdateDto;
import com.gman97.cinemachain.entity.Movie;
import com.gman97.cinemachain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PosterUpdateMapper implements Mapper<PosterUpdateDto, Movie> {

    private final MovieRepository movieRepository;

    @Override
    public Movie map(PosterUpdateDto object) {
        return movieRepository.findById(object.getMovieId())
                .map(movie -> {
                    movie.setPoster(object.getPoster().getOriginalFilename());
                    return movie;
                })
                .orElseThrow(RuntimeException::new);
    }
}
