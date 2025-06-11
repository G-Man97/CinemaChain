--liquibase formatted sql

--changeset gman97:1
ALTER TABLE movies
ADD COLUMN is_it_in_rent BOOLEAN NOT NULL DEFAULT false;

--changeset gman97:2
ALTER TABLE movies
ADD COLUMN actors VARCHAR(256) NOT NULL DEFAULT '';
