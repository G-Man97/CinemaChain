--liquibase formatted sql

--changeset gman97:1
CREATE TABLE seats_for_film_sessions
(
    id              BIGSERIAL PRIMARY KEY,
    row             SMALLINT    NOT NULL,
    seat_no         SMALLINT    NOT NULL,
    status          VARCHAR(32) NOT NULL,
    film_session_id BIGINT REFERENCES film_sessions (id)
);


