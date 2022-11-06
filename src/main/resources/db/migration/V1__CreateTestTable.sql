CREATE TABLE IF NOT EXISTS writers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS labels (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS posts (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL,
    post_status VARCHAR(255) NOT NULL,
    writer_id BIGINT,
    FOREIGN KEY (writer_id)
        REFERENCES writers (id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS post_label (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT,
    label_id BIGINT,
    FOREIGN KEY (post_id)
        REFERENCES posts (id)
        ON DELETE CASCADE,
    FOREIGN KEY (label_id)
        REFERENCES labels (id)
        ON DELETE CASCADE
);