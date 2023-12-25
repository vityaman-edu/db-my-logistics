CREATE TABLE consume (
  id        serial    PRIMARY KEY,
  source_id integer   NOT NULL REFERENCES storage(id),
  moment    timestamp NOT NULL
);

CREATE TABLE consume_atom (
  id           serial       PRIMARY KEY,
  consume_id   integer      NOT NULL REFERENCES consume(id),
  item_kind_id integer      NOT NULL REFERENCES item_kind(id),
  amount       positive_int NOT NULL
);