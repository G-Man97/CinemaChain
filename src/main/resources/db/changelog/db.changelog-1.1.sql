--liquibase formatted sql

--changeset gman97:1
ALTER TABLE movies
ADD COLUMN countries VARCHAR(64) DEFAULT '' NOT NULL;

--changeset gman97:2
ALTER TABLE movies
ADD COLUMN producer VARCHAR(64) DEFAULT '' NOT NULL;

--changeset gman97:3
ALTER TABLE movies
ADD COLUMN genre VARCHAR(64) DEFAULT '' NOT NULL;

--changeset gman97:4
ALTER TABLE movies
ADD COLUMN composer VARCHAR(64) DEFAULT '' NOT NULL;
