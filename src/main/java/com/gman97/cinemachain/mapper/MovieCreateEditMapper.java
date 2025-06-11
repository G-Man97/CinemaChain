package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.MovieCreateEditDto;
import com.gman97.cinemachain.entity.Movie;
import com.gman97.cinemachain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class MovieCreateEditMapper implements Mapper<MovieCreateEditDto, Movie> {

    private final MovieRepository movieRepository;

    @Override
    public Movie map(MovieCreateEditDto object) {

        Optional<Movie> mayBeSavedMovie = object.getId() != null
                ? object.getIsItInRent()
                        ? movieRepository.findByIdWithFetch(object.getId())
                        : movieRepository.findById(object.getId())
                : Optional.empty();

        return new Movie(
                object.getId(),
                object.getTitle(),
                object.getPremiere(),
                object.getRentEnd(),
                object.getDuration(),
                object.getPegi(),
                object.getGenre(),
                object.getCountries(),
                object.getComposer(),
                object.getProducer(),
                object.getActors(),
                object.getDescription(),
                Optional.ofNullable(object.getPoster())
                        .filter(not(MultipartFile::isEmpty))
                        .map(MultipartFile::getOriginalFilename)
                        .orElseGet(() -> mayBeSavedMovie.map(Movie::getPoster).orElse(null)),
                mayBeSavedMovie.map(Movie::getIsItInRent).orElse(Boolean.FALSE),
                mayBeSavedMovie.map(Movie::getFilmSessions).orElse(new ArrayList<>())
        );

    }
}
