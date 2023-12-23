CREATE TABLE transporter (
  id SERIAL PRIMARY KEY,
  name VARCHAR(127) CHECK (name ~ '[a-zA-Z''-]{3,127}'),
  note TEXT
);

CREATE TABLE transfer_request (
  id SERIAL PRIMARY KEY,
  manager_id INT NOT NULL REFERENCES manager(id),
  src_storage_id INT NOT NULL REFERENCES storage(id),
  dst_storage_id INT NOT NULL REFERENCES storage(id),
  transporter_id INT NOT NULL REFERENCES transporter(id),
  expected_begin_date DATE NOT NULL,
  expected_duration INTERVAL NOT NULL
);

CREATE TABLE transfer_request_approval (
  id SERIAL PRIMARY KEY,
  admin_id INT NOT NULL REFERENCES admin(id),
  transfer_request_id INT NOT NULL REFERENCES transfer_request(id),
  date DATE NOT NULL
);

CREATE TABLE transfer (
  id SERIAL PRIMARY KEY,
  transfer_request_id INT NOT NULL REFERENCES transfer_request(id),
  actual_begin_date DATE NOT NULL,
  actual_duration INTERVAL NOT NULL
);

CREATE TABLE transfer_group (
  transfer_request_id INT NOT NULL REFERENCES transfer_request(id),
  item_group_id INT NOT NULL REFERENCES item_group(id),
  PRIMARY KEY (transfer_request_id, item_group_id)
);