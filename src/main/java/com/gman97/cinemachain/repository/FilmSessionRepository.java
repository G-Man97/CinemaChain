package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.dto.SeanceInfoDto;
import com.gman97.cinemachain.entity.FilmSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {

@Query(value = "SELECT fs.date            AS date, " +
                      "fs.begin_session   AS beginSession, " +
                      "m.duration         AS duration, " +
                      "ch.number          AS hallNumber, " +
                      "m.title            AS movieTitle, " +
                      "c.name             AS cinemaName, " +
                      "fs.id              AS filmSessionId " +
                "FROM film_sessions fs " +
                "JOIN cinema_halls  ch  ON fs.cinema_hall_id = ch.id " +
                "JOIN movies         m  ON fs.movie_id = m.id " +
                "JOIN cinemas        c  ON ch.cinema_id = c.id " +
                "WHERE fs.id = :filmSessionId",
        nativeQuery = true)
    Optional<SeanceInfoDto> findSeanceInfoById(Long filmSessionId);

@Query("select fs from FilmSession fs " +
        "join fetch fs.movie " +
        "where fs.cinemaHall.id = :cinemaHallId " +
        "and fs.date = :date " +
        "and fs.beginSession between :startInterval and :endInterval")
    List<FilmSession> findAllByCinemaHallIdAndInterval(Integer cinemaHallId, LocalDate date, LocalTime startInterval, LocalTime endInterval);
}
