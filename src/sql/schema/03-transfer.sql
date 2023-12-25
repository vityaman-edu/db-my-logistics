CREATE TABLE transfer (
  id              serial    PRIMARY KEY,

  source_id       integer   NOT NULL REFERENCES storage(id),
  target_id       integer   NOT NULL REFERENCES storage(id),

  withdraw_moment timestamp NOT NULL,
  duration        interval  NOT NULL,

  initiator_id        integer   NOT NULL REFERENCES manager(id),
  source_approver_id  integer   REFERENCES admin(id),
  target_approver_id  integer   REFERENCES admin(id),

  creation_moment timestamp NOT NULL DEFAULT current_timestamp
);

CREATE TABLE transfer_atom (
  id                  serial        PRIMARY KEY,
  transfer_id         integer       NOT NULL REFERENCES transfer(id),
  item_kind_id        integer       NOT NULL REFERENCES item_kind(id),
  amount              positive_int  NOT NULL
);
