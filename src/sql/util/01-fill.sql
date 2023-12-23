INSERT INTO location (name) VALUES
  ('Moscow'), ('Omsk'), ('Kostroma');

INSERT INTO storage (name, location_id) 
SELECT location.name || ' Storage #1' as name, id as location_id
FROM location;

INSERT INTO item_kind (name, unit) VALUES
  ('Milk', 'milliliter'), ('Rock', 'gram'), ('Potato', 'gram');

INSERT INTO cell (storage_id, item_kind_id, capacity)
SELECT storage.id, item_kind.id, 10
FROM storage, item_kind
WHERE storage.name   = 'Moscow Storage #1'
  AND item_kind.name = 'Rock';
  
INSERT INTO cell (storage_id, item_kind_id, capacity)
SELECT storage.id, item_kind.id, 20
FROM storage, item_kind
WHERE storage.name   = 'Omsk Storage #1'
  AND item_kind.name = 'Potato';

INSERT INTO cell (storage_id, item_kind_id, capacity)
SELECT storage.id, item_kind.id, 40
FROM storage, item_kind
WHERE storage.name   = 'Omsk Storage #1'
  AND item_kind.name = 'Milk';


INSERT INTO users (nickname, first_name, last_name) VALUES
  ('vitya-smirnov', 'Vitya', 'Smirnov'),
  ('petya-ivanov', 'Petya', 'Ivanov'),
  ('margo-kuprina', 'Margarita', 'Kuprina');

INSERT INTO manager (user_id)
SELECT users.id as user_id 
FROM users WHERE nickname = 'margo-kuprina';

INSERT INTO admin (user_id, storage_id)
SELECT users.id as user_id, storage.id as storage_id
FROM users, storage
WHERE users.nickname = 'vitya-smirnov'
  AND storage.name = 'Omsk Storage #1';

INSERT INTO admin (user_id, storage_id)
SELECT users.id as user_id, storage.id as storage_id
FROM users, storage
WHERE users.nickname = 'petya-ivanov'
  AND storage.name = 'Moscow Storage #1';


INSERT INTO users (nickname, first_name, last_name) VALUES
  ('consumer-5ka', 'Consumer', 'Consumer');

INSERT INTO storage (name, location_id) 
SELECT 'consumer-storage-5ka' as name, id as location_id
FROM location WHERE location.name = 'Moscow';

INSERT INTO cell (storage_id, item_kind_id, capacity)
SELECT storage.id, item_kind.id, 4000
FROM storage, item_kind
WHERE storage.name   = 'consumer-storage-5ka'
  AND item_kind.name = 'Milk';

INSERT INTO cell (storage_id, item_kind_id, capacity)
SELECT storage.id, item_kind.id, 100000000
FROM storage, item_kind
WHERE storage.name   = 'consumer-storage-5ka'
  AND item_kind.name = 'Rock';

INSERT INTO consumer (name, note, storage_id)
SELECT '5ka Shops Net'::text, 'He''s a strange guy.'::text, storage.id
FROM storage WHERE storage.name = 'consumer-storage-5ka';

INSERT INTO item_order (consumer_id, note)
SELECT consumer.id, 'Need some milk and heavy rock!'
FROM consumer WHERE consumer.name = '5ka Shops Net';

INSERT INTO item_group (order_id, item_kind_id, amount)
SELECT item_order.id, item_kind.id, 1000
FROM item_order, item_kind
WHERE item_order.note = 'Need some milk and heavy rock!'
  AND item_kind.name = 'Milk';

INSERT INTO item_group (order_id, item_kind_id, amount)
SELECT item_order.id, item_kind.id, 100000000
FROM item_order, item_kind
WHERE item_order.note = 'Need some milk and heavy rock!'
  AND item_kind.name = 'Rock';

INSERT INTO transporter (name, note) VALUES
  ('Arlanbek', 'Yandex Taxi Rating 5.0');

INSERT INTO transfer_request (
  manager_id, src_storage_id, dst_storage_id, transporter_id, 
  expected_begin_date, expected_duration
)
SELECT 
  manager.id, src.id, dst.id, transporter.id, 
  '2011-01-01'::date, '2 hours'::interval
FROM manager, storage as src, storage as dst, transporter, users
WHERE users.nickname = 'margo-kuprina'
  AND src.name = 'Moscow Storage #1'
  AND dst.name = 'consumer-storage-5ka'
  AND transporter.name = 'Arlanbek'
  AND manager.user_id = users.id;

INSERT INTO transfer_group (transfer_request_id, item_group_id)
SELECT transfer_request.id, item_group.id
FROM transfer_request, item_group;
