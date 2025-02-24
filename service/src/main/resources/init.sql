CREATE TABLE users
(
    id              UUID PRIMARY KEY,
    password        VARCHAR(255)        NOT NULL,
    first_name      VARCHAR(50)         NOT NULL,
    last_name       VARCHAR(50)         NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    role            VARCHAR(32)         NOT NULL,
    profile_picture VARCHAR(255),
    birth_date      DATE                NOT NULL,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE post
(
    id           UUID PRIMARY KEY,
    user_id      UUID         NOT NULL REFERENCES
        users (id) ON DELETE CASCADE,
    title        VARCHAR(255) NOT NULL,
    text         TEXT         NOT NULL,
    post_picture VARCHAR(255),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE likes
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    post_id    UUID NOT NULL REFERENCES post (id) ON DELETE CASCADE,
    created_at TIMESTAMP,
    CONSTRAINT unique_user_per_like UNIQUE (user_id, post_id)
);

drop table likes;


CREATE TABLE friendship
(
    id         UUID PRIMARY KEY,
    user_id    UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    friend_id  UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status     VARCHAR(32) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT check_different_users CHECK (user_id <> friend_id),
    CONSTRAINT friendship_uniqueness UNIQUE (user_id, friend_id)
);

CREATE TABLE comment
(
    id         UUID PRIMARY KEY,
    post_id    UUID NOT NULL REFERENCES post (id) ON DELETE CASCADE,
    user_id    UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    text       TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

DROP TABLE friendship;

