package com.gman97.cinemachain.repository;


import com.gman97.cinemachain.entity.HallSize;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class SeatsCreateForSessionRepositoryImpl implements SeatsCreateForSessionRepository{

    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL = """
            INSERT INTO seats_for_film_sessions (row, seat_no, status, film_session_id, ticket_cost)
            (
                SELECT
                    row,
                    seat_no,
                    status,
                    %s,
                    %s
                FROM hall_seats
                WHERE %s_hall_seat_exists = true
            )
            """;

    @Override
    public void createSeatsForFilmSession(Long filmSessionId, HallSize hallSize, Integer ticketCost) {
        jdbcTemplate.execute(CREATE_TABLE_SQL.formatted(filmSessionId,
                ticketCost, hallSize.toString().toLowerCase()));
    }
}
