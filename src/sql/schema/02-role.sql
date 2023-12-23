CREATE DOMAIN nickname AS VARCHAR(31) 
CHECK (VALUE ~ '[a-z-]{3,31}');

CREATE DOMAIN person_name AS VARCHAR(31) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,31}');

CREATE TABLE users (
  id         SERIAL      PRIMARY KEY,
  nickname   nickname    NOT NULL UNIQUE,
  first_name person_name NOT NULL,
  last_name  person_name NOT NULL
);

CREATE TABLE manager (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id)
);

CREATE TABLE admin (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id),
  storage_id INT NOT NULL REFERENCES storage(id),
  UNIQUE (user_id, storage_id)
);