CREATE TABLE users (
  id              serial      PRIMARY KEY,
  nickname        nickname    NOT NULL UNIQUE,
  first_name      person_name NOT NULL,
  last_name       person_name NOT NULL,
  creation_moment timestamp   NOT NULL DEFAULT current_timestamp
);

CREATE TABLE manager (
  id              serial     PRIMARY KEY,
  user_id         integer    NOT NULL REFERENCES users(id),
  creation_moment timestamp  NOT NULL DEFAULT current_timestamp
);

CREATE TABLE admin (
  id              serial    PRIMARY KEY,
  user_id         integer   NOT NULL REFERENCES users(id),
  storage_id      integer   NOT NULL REFERENCES storage(id),
  creation_moment timestamp NOT NULL DEFAULT current_timestamp,
  UNIQUE (user_id, storage_id)
);