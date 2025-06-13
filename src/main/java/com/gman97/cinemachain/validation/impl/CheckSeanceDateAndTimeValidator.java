package com.gman97.cinemachain.validation.impl;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.repository.FilmSessionRepository;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.validation.CheckSeanceDateAndTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class CheckSeanceDateAndTimeValidator implements ConstraintValidator<CheckSeanceDateAndTime, FilmSessionCreateDto> {

    private final MovieRepository movieRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final long MAX_FILM_LENGTH = 250L;
    private final long BREAK_BETWEEN_SESSIONS = 10L;

    @Transactional(readOnly = true)
    @Override
    public boolean isValid(FilmSessionCreateDto value, ConstraintValidatorContext context) {
        var date = value.getDate();
        var time = value.getBeginSession();

        if (time == null || date == null || !date.isAfter(LocalDate.now())
                || time.isBefore(LocalTime.of(9, 1))) {
            return true; // избегаем NPE и откладываем до последнего запрос в БД ниже, проверяя корректность даты и
                         // времени (эти же проверки проводят другие аннотации над этими полями в FilmSessionCreateDto)
        }

        var mayBeMovie = movieRepository.findById(value.getMovieId());

        if (mayBeMovie.isPresent()) {
            var movieDuration = mayBeMovie.get().getDuration();
            var startInterval = time.minusMinutes(MAX_FILM_LENGTH);
            var endInterval = time.plusMinutes(movieDuration + BREAK_BETWEEN_SESSIONS);

            return filmSessionRepository.findAllByCinemaHallIdAndInterval(value.getCinemaHallId(), value.getDate(), startInterval, endInterval)
                    .stream()
                    .allMatch(fs -> {
                        var beginSession = fs.getBeginSession();
                        var duration = fs.getMovie().getDuration();

                        return beginSession.isBefore(time)
                                    ? time.isAfter(beginSession.plusMinutes(duration + BREAK_BETWEEN_SESSIONS))
                                    : time.isBefore(beginSession.minusMinutes(movieDuration + BREAK_BETWEEN_SESSIONS));
                    });
        }

        return false;
    }
}
