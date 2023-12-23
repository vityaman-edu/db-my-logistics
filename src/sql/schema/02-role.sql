CREATE TABLE users (
  id         serial      PRIMARY KEY,
  nickname   nickname    NOT NULL UNIQUE,
  first_name person_name NOT NULL,
  last_name  person_name NOT NULL
);

CREATE TABLE manager (
  id      serial     PRIMARY KEY,
  user_id int        NOT NULL REFERENCES users(id)
);

CREATE TABLE admin (
  id         serial PRIMARY KEY,
  user_id    int    NOT NULL REFERENCES users(id),
  storage_id int    NOT NULL REFERENCES storage(id),
  UNIQUE (user_id, storage_id)
);