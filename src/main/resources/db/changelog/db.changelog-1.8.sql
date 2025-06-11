--liquibase formatted sql

--changeset gman97:1
ALTER TABLE movies
ADD COLUMN poster VARCHAR(128);