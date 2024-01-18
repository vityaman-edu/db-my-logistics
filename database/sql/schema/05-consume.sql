CREATE TABLE consume (
  id              serial    PRIMARY KEY,
  source_id       integer   NOT NULL REFERENCES storage(id),
  moment          timestamp NOT NULL,
  creation_moment timestamp NOT NULL DEFAULT current_timestamp
);

CREATE INDEX consume_moment_idx 
ON consume
USING btree(moment);

CREATE TABLE consume_atom (
  id           serial       PRIMARY KEY,
  consume_id   integer      NOT NULL REFERENCES consume(id),
  item_kind_id integer      NOT NULL REFERENCES item_kind(id),
  amount       positive_int NOT NULL
);