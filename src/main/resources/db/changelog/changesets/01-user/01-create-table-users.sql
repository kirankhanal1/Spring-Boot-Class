-- liquibase formatted sql
-- changeset kiran.khanal:users-create-v1 context:dev
-- preconditions onFail:CONTINUE onError: HALT
CREATE TABLE IF NOT EXISTS `users`(
    id          INT             AUTO_INCREMENT      NOT NULL,
    username    VARCHAR(50)     NOT NULL,
    full_name   VARCHAR(100)    NOT NULL,
    email       VARCHAR(30)     NOT NULL            UNIQUE,
    password    VARCHAR(15)     NOT NULL,
    is_deleted  Boolean         NOT NULL            DEFAULT FALSE,
    created_at  TIMESTAMP       NOT NULL,
    updated_at  TIMESTAMP       NOT NULL,
    CONSTRAINT pk_users   PRIMARY KEY (id)
);