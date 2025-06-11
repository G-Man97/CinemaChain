--liquibase formatted sql

--changeset gman97:1
ALTER TABLE tickets
ADD COLUMN date DATE DEFAULT '2025-01-01' NOT NULL;