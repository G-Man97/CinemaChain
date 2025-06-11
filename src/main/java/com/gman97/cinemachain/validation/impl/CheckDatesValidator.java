package com.gman97.cinemachain.validation.impl;

import com.gman97.cinemachain.dto.MovieCreateEditDto;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.validation.CheckDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CheckDatesValidator implements ConstraintValidator<CheckDates, MovieCreateEditDto> {

    private final MovieRepository movieRepository;

    @Override
    public boolean isValid(MovieCreateEditDto value, ConstraintValidatorContext context) {
        var premiere = value.getPremiere();
        var rentEnd = value.getRentEnd();
        var today = LocalDate.now();

        if (premiere == null || rentEnd == null) {
            return true; // Чтобы избежать NullPointerException далее в этом методе
        }

        if (value.getIsItInRent()) {

            if ((premiere.isBefore(today) || premiere.isEqual(today))
                    && (rentEnd.isAfter(today) || rentEnd.isEqual(today))) {

                var mayBeMovie = movieRepository.findById(value.getId());

                if (mayBeMovie.isPresent()) {
                    var movie = mayBeMovie.get();
                    var repoPremiere = movie.getPremiere();
                    var repoRentEnd = movie.getRentEnd();

                    if (repoPremiere.isEqual(premiere)
                            && repoRentEnd.isEqual(rentEnd)) {
                        return true; // валидно, когда даты не редактировались

                    } else if (!repoPremiere.isEqual(premiere)
                            && repoRentEnd.isEqual(rentEnd)) {
                        return false; //не можем менять дату премьеры, когда фильм уже в прокате

                    } else return repoPremiere.isEqual(premiere) // если поменяли дату конца проката, то разница
                            && !repoRentEnd.isEqual(rentEnd)     // между premiere и rentEnd должна быть минимум 7 дней,
                            && premiere.plusDays(6L).isBefore(rentEnd); // включая дату премьеры
                }
            } else return false;

        }

        return premiere.isAfter(today)
                && rentEnd.isAfter(today)
                && premiere.plusDays(6L).isBefore(rentEnd);
    }
}
