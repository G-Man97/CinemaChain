package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("select m from Movie m " +
            "join fetch m.filmSessions fs " +
            "join fetch fs.cinemaHall ch " +
            "join fetch ch.cinema c " +
            "where m.id = :id")
    Optional<Movie> findByIdWithFetch(Integer id);

    @Query("select m from Movie m " +
            "where m.isItInRent = :isItInRent")
    List<Movie> findAllByIsItInRent(Boolean isItInRent);
}
