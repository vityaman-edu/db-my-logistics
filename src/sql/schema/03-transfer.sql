CREATE TABLE transfer_request (
  id                  serial    PRIMARY KEY,
  manager_id          integer   NOT NULL REFERENCES manager(id),
  src_storage_id      integer   NOT NULL REFERENCES storage(id),
  dst_storage_id      integer   NOT NULL REFERENCES storage(id),
  expected_begin_date date      NOT NULL,
  expected_duration   interval  NOT NULL
);

CREATE TABLE transfer_atom (
  id                  serial        PRIMARY KEY,
  transfer_request_id integer       NOT NULL REFERENCES transfer_request(id),
  item_kind_id        integer       NOT NULL REFERENCES item_kind(id),
  amount              positive_int  NOT NULL
);

CREATE TABLE transfer_fact (
  id                  serial    PRIMARY KEY,
  transfer_request_id integer   NOT NULL REFERENCES transfer_request(id),
  actual_begin_date   date      NOT NULL,
  actual_duration     interval  NOT NULL
);