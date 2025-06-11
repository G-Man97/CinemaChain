--liquibase formatted sql

--changeset gman97:1
ALTER TABLE seats_for_film_sessions
ADD COLUMN ticket_cost INTEGER DEFAULT 0 NOT NULL;