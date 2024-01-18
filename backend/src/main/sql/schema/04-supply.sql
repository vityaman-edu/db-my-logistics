CREATE TABLE supply (
  id              serial    PRIMARY KEY,
  target_id       integer   NOT NULL REFERENCES storage(id),
  moment          timestamp NOT NULL,
  creation_moment timestamp NOT NULL DEFAULT current_timestamp
);

CREATE INDEX supply_moment_idx 
ON supply
USING btree(moment);

CREATE TABLE supply_atom (
  id           serial       PRIMARY KEY,
  supply_id    integer      NOT NULL REFERENCES supply(id),
  item_kind_id integer      NOT NULL REFERENCES item_kind(id),
  amount       positive_int NOT NULL
);