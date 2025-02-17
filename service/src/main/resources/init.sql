CREATE TABLE users
(
    id              UUID PRIMARY KEY,
    user_name       VARCHAR(50) UNIQUE  NOT NULL,
    password        VARCHAR(255)        NOT NULL,
    first_name      VARCHAR(50)         NOT NULL,
    last_name       VARCHAR(50)         NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    role            VARCHAR(10)         NOT NULL,
    profile_picture BYTEA,
    birth_date      DATE                NOT NULL,
    created_on      TIMESTAMP,
    updated_on      TIMESTAMP
);

CREATE TABLE post
(
    id           UUID PRIMARY KEY,
    user_id      UUID         NOT NULL,
    title        VARCHAR(255) NOT NULL,
    text         TEXT         NOT NULL,
    post_picture BYTEA,
    created_on   TIMESTAMP,
    updated_on   TIMESTAMP
);

CREATE TABLE likes
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    post_id    UUID NOT NULL,
    created_on TIMESTAMP
);

CREATE TABLE friendship
(
    id         UUID PRIMARY KEY,
    user1_id   UUID        NOT NULL,
    user2_id   UUID        NOT NULL,
    status     VARCHAR(20) NOT NULL,
    created_on TIMESTAMP,
    updated_on TIMESTAMP
);

CREATE TABLE comment
(
    id         UUID PRIMARY KEY,
    post_id    UUID NOT NULL,
    user_id    UUID NOT NULL,
    text       TEXT NOT NULL,
    created_on TIMESTAMP,
    updated_on TIMESTAMP
);
