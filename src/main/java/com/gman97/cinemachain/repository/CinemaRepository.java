package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
}
