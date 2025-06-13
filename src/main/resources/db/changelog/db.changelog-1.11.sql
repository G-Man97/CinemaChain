--liquibase formatted sql

--changeset gman97:1
ALTER TABLE movies
ALTER COLUMN duration TYPE INTEGER USING duration::integer;
