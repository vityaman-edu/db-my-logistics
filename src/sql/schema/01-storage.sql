CREATE TABLE location (
  id SERIAL PRIMARY KEY,
  name VARCHAR(63) NOT NULL UNIQUE CHECK (name ~ '[a-zA-Z''-]{5,63}')
);

CREATE TABLE storage (
  id SERIAL PRIMARY KEY,
  name VARCHAR(63) NOT NULL UNIQUE CHECK (name ~ '[a-zA-Z''-]{5,63}'),
  location_id INT NOT NULL REFERENCES location(id)
);

CREATE TABLE item_kind (
  id SERIAL PRIMARY KEY,
  name VARCHAR(127) NOT NULL CHECK (name ~ '[a-zA-Z''-]{3,127}'),
  unit VARCHAR(63) NOT NULL CHECK (unit ~ '[a-z-.]{3,63}')
);

CREATE TABLE cell (
  id SERIAL PRIMARY KEY,
  storage_id INT NOT NULL REFERENCES storage(id),
  item_kind_id INT NOT NULL REFERENCES item_kind(id),
  capacity INT NOT NULL CHECK (capacity > 0)
);
