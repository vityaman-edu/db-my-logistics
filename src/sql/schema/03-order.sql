CREATE TABLE consumer (
  id SERIAL PRIMARY KEY,
  name VARCHAR(127) CHECK (name ~ '[a-zA-Z''-]{3,127}'),
  note TEXT NOT NULL,
  storage_id INT NOT NULL REFERENCES storage(id)
);

CREATE TABLE item_order (
  id SERIAL PRIMARY KEY,
  consumer_id INT NOT NULL REFERENCES consumer(id),
  note TEXT NOT NULL
);

CREATE TABLE item_group (
  id SERIAL PRIMARY KEY,
  order_id INT NOT NULL REFERENCES item_order(id),
  item_kind_id INT NOT NULL REFERENCES item_kind(id),
  amount INT NOT NULL CHECK (amount > 0)
);