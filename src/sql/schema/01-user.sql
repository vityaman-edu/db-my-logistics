CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  nickname nickname NOT NULL UNIQUE,
  first_name name NOT NULL,
  last_name name NOT NULL
);

CREATE TABLE admin (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id)
);

CREATE TABLE manager (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id)
);