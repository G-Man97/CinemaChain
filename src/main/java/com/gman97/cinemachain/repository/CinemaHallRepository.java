package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.entity.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Integer> {

    @Query("select ch from CinemaHall ch " +
            "where ch.cinema.id = :id")
    List<CinemaHall> findAllByCinemaId(Integer id);
}
