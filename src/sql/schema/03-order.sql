CREATE TABLE consumer (
  id         serial        PRIMARY KEY,
  name       consumer_name NOT NULL,
  note       text          NOT NULL,
  storage_id int           NOT NULL REFERENCES storage(id)
);

CREATE TABLE item_order (
  id          serial PRIMARY KEY,
  consumer_id int    NOT NULL REFERENCES consumer(id),
  note        text   NOT NULL
);

CREATE TABLE item_group (
  id           serial       PRIMARY KEY,
  order_id     int          NOT NULL REFERENCES item_order(id),
  item_kind_id int          NOT NULL REFERENCES item_kind(id),
  amount       positive_int NOT NULL,
  UNIQUE (order_id, item_kind_id)
);