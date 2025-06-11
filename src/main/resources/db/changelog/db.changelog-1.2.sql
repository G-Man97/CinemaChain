--liquibase formatted sql

--changeset gman97:1
ALTER TABLE film_sessions
ALTER COLUMN begin_session TYPE TIME;

--changeset gman97:2
ALTER TABLE film_sessions
ADD COLUMN date DATE NOT NULL DEFAULT '2000-01-01';
