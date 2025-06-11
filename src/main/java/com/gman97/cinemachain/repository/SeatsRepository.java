package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.entity.SeatsForFilmSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatsRepository extends JpaRepository<SeatsForFilmSession, Long>, SeatsCreateForSessionRepository {

    @Query("select s from SeatsForFilmSession s " +
            "where s.filmSession.id = :filmSessionId " +
            "order by s.row, s.seatNo")
    List<SeatsForFilmSession> findByFilmSessionId(Long filmSessionId);

    @Modifying
    @Query("update SeatsForFilmSession s " +
            "set s.status = 'TAKEN' " +
            "where s.id in (:seatIds) " +
            "and s.status = 'FREE'"
    )
    int updateStatusForBoughtSeats(List<Long> seatIds);
}
