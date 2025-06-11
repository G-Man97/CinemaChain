--liquibase formatted sql

--changeset gman97:1
ALTER TABLE hall_seats
ADD COLUMN status VARCHAR(32) NOT NULL DEFAULT 'FREE';
