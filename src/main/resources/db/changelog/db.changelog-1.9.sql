--liquibase formatted sql

--changeset gman97:1
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL ,
    role VARCHAR(16) NOT NULL
);