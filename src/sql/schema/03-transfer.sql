CREATE TABLE transfer (
  id                  serial    PRIMARY KEY,
  src_storage_id      integer   NOT NULL REFERENCES storage(id),
  dst_storage_id      integer   NOT NULL REFERENCES storage(id)
);

CREATE TABLE transfer_atom (
  id                  serial        PRIMARY KEY,
  transfer_id         integer       NOT NULL REFERENCES transfer(id),
  item_kind_id        integer       NOT NULL REFERENCES item_kind(id),
  amount              positive_int  NOT NULL
);
