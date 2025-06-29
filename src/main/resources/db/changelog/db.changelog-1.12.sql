--liquibase formatted sql

--changeset gman97:1
ALTER TABLE tickets
ALTER COLUMN cost TYPE INTEGER;
