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
        users (id),
    title        VARCHAR(255) NOT NULL,
    text         TEXT         NOT NULL,
    post_picture VARCHAR(255),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE likes
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL REFERENCES users (id),
    post_id    UUID NOT NULL REFERENCES post (id),
    created_at TIMESTAMP,
    CONSTRAINT unique_user_per_like UNIQUE (user_id, post_id)
);

CREATE TABLE friendship
(
    id         UUID PRIMARY KEY,
    user1_id   UUID        NOT NULL REFERENCES users (id),
    user2_id   UUID        NOT NULL REFERENCES users (id),
    status     VARCHAR(32) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
    CONSTRAINT check_different_users CHECK (user1_id <> user2_id)
);

CREATE TABLE comment
(
    id         UUID PRIMARY KEY,
    post_id    UUID NOT NULL REFERENCES post (id),
    user_id    UUID NOT NULL REFERENCES users (id),
    text       TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
