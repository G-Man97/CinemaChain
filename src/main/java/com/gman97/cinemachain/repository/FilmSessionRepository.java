package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.dto.SeanceInfoDto;
import com.gman97.cinemachain.entity.FilmSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {

@Query(value = "select fs.date          as date, " +
                    "fs.begin_session   as beginSession, " +
                    "m.duration         as duration, " +
                    "ch.number          as hallNumber, " +
                    "m.title            as movieTitle, " +
                    "c.name             as cinemaName, " +
                    "fs.id              as filmSessionId " +
                "from film_sessions fs " +
                "join cinema_halls  ch  on fs.cinema_hall_id = ch.id " +
                "join movies         m  on fs.movie_id = m.id " +
                "join cinemas        c  on ch.cinema_id = c.id " +
                "where fs.id = :filmSessionId",
        nativeQuery = true)
    Optional<SeanceInfoDto> findSeanceInfoById(Long filmSessionId);
}
