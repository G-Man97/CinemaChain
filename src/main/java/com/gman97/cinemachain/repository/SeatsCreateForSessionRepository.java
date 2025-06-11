package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.entity.HallSize;

public interface SeatsCreateForSessionRepository {

    void createSeatsForFilmSession(Long filmSessionId, HallSize hallSize, Integer ticketCost);
}
