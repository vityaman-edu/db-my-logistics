CREATE TABLE transfer (
  id              serial    PRIMARY KEY,
  source_id       integer   NOT NULL REFERENCES storage(id),
  target_id       integer   NOT NULL REFERENCES storage(id),
  withdraw_moment timestamp NOT NULL,
  duration        interval  NOT NULL,
  creation_moment timestamp NOT NULL DEFAULT current_timestamp
);

CREATE TABLE transfer_atom (
  id                  serial        PRIMARY KEY,
  transfer_id         integer       NOT NULL REFERENCES transfer(id),
  item_kind_id        integer       NOT NULL REFERENCES item_kind(id),
  amount              positive_int  NOT NULL
);
