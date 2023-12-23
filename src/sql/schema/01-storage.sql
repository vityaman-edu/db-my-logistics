CREATE TABLE location (
  id   serial        PRIMARY KEY,
  name location_name NOT NULL UNIQUE
);

CREATE TABLE storage (
  id          serial       PRIMARY KEY,
  name        storage_name NOT NULL UNIQUE,
  location_id int          NOT NULL REFERENCES location(id)
);

CREATE TABLE item_kind (
  id   serial         PRIMARY KEY,
  name item_kind_name NOT NULL,
  unit item_kind_unit NOT NULL
);

CREATE TABLE cell (
  id           serial       PRIMARY KEY,
  storage_id   int          NOT NULL REFERENCES storage(id),
  item_kind_id int          NOT NULL REFERENCES item_kind(id),
  capacity     positive_int NOT NULL
);
