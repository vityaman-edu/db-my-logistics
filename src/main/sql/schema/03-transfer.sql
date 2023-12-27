CREATE TABLE transfer (
  id              serial    PRIMARY KEY,

  source_id       integer   NOT NULL REFERENCES storage(id),
  target_id       integer   NOT NULL REFERENCES storage(id),

  withdraw_moment timestamp NOT NULL,
  income_moment   timestamp NOT NULL,

  initiator_id        integer   NOT NULL REFERENCES manager(id),
  source_approver_id  integer   REFERENCES admin(id),
  target_approver_id  integer   REFERENCES admin(id),

  creation_moment timestamp NOT NULL DEFAULT current_timestamp,

  CONSTRAINT transfer_is_not_loop 
  CHECK (source_id != target_id),

  CONSTRAINT transfer_withdraw_before_income
  CHECK (withdraw_moment <= income_moment)
);

CREATE INDEX transfer_moment_idx 
ON transfer
USING btree(withdraw_moment)
WHERE source_approver_id IS NOT NULL 
  AND target_approver_id IS NOT NULL;

CREATE INDEX transfer_moment_idx 
ON transfer
USING btree(income_moment)
WHERE source_approver_id IS NOT NULL 
  AND target_approver_id IS NOT NULL;

CREATE TABLE transfer_atom (
  id                  serial        PRIMARY KEY,
  transfer_id         integer       NOT NULL REFERENCES transfer(id),
  item_kind_id        integer       NOT NULL REFERENCES item_kind(id),
  amount              positive_int  NOT NULL
);
