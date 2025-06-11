--liquibase formatted sql

--changeset gman97:1
CREATE TABLE cinemas (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeset gman97:2
CREATE TABLE cinema_halls (
    id SERIAL PRIMARY KEY ,
    number INTEGER NOT NULL ,
    size VARCHAR(32) NOT NULL ,
    cinema_id INTEGER REFERENCES cinemas(id)
);

--changeset gman97:3
CREATE TABLE movies (
    id SERIAL PRIMARY KEY ,
    title VARCHAR(32) NOT NULL ,
    premiere DATE NOT NULL ,
    rent_end DATE NOT NULL ,
    duration VARCHAR(16) NOT NULL ,
    pegi VARCHAR(16) NOT NULL ,
    description TEXT NOT NULL
);

--changeset gman97:4
CREATE TABLE film_sessions (
    id BIGSERIAL PRIMARY KEY ,
    begin_session TIMESTAMP NOT NULL ,
    movie_id INTEGER REFERENCES movies(id) ,
    cinema_hall_id INTEGER REFERENCES cinema_halls(id)
);

--changeset gman97:5
CREATE TABLE tickets (
    id BIGSERIAL PRIMARY KEY ,
    movie_title VARCHAR(32) NOT NULL ,
    movie_duration VARCHAR(16) NOT NULL ,
    begin_session TIMESTAMP NOT NULL ,
    cinema_name VARCHAR(64) NOT NULL ,
    hall_number INTEGER NOT NULL ,
    row SMALLINT NOT NULL ,
    seat_no SMALLINT NOT NULL ,
    cost NUMERIC(6, 2) NOT NULL ,
    film_session_id BIGINT REFERENCES film_sessions(id)
);

--changeset gman97:6
CREATE TABLE hall_seats (
    id SMALLSERIAL PRIMARY KEY ,
    row SMALLINT NOT NULL ,
    seat_no SMALLINT NOT NULL ,
    small_hall_seat_exists BOOLEAN NOT NULL ,
    middle_hall_seat_exists BOOLEAN NOT NULL ,
    big_hall_seat_exists BOOLEAN NOT NULL
);


