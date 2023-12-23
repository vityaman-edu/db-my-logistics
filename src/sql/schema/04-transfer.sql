CREATE TABLE transporter (
  id   serial           PRIMARY KEY,
  name transporter_name NOT NULL,
  note TEXT             NOT NULL
);

CREATE TABLE transfer_request (
  id                  serial    PRIMARY KEY,
  manager_id          int       NOT NULL REFERENCES manager(id),
  src_storage_id      int       NOT NULL REFERENCES storage(id),
  dst_storage_id      int       NOT NULL REFERENCES storage(id),
  transporter_id      int       NOT NULL REFERENCES transporter(id),
  expected_begin_date date      NOT NULL,
  expected_duration   interval  NOT NULL
);

CREATE TABLE transfer_request_approval (
  id                  serial    PRIMARY KEY,
  admin_id            int       NOT NULL REFERENCES admin(id),
  transfer_request_id int       NOT NULL REFERENCES transfer_request(id),
  date                date      NOT NULL
);

CREATE TABLE transfer (
  id                  serial    PRIMARY KEY,
  transfer_request_id int       NOT NULL REFERENCES transfer_request(id),
  actual_begin_date   date      NOT NULL,
  actual_duration     interval  NOT NULL
);

CREATE TABLE transfer_group (
  transfer_request_id int NOT NULL REFERENCES transfer_request(id),
  item_group_id       int NOT NULL REFERENCES item_group(id),
  PRIMARY KEY (transfer_request_id, item_group_id)
);