CREATE FUNCTION fill() RETURNS void AS $$
DECLARE 
  moscow_id   integer;
  omsk_id     integer;
  kostroma_id integer;

  milk_id     integer;
  rock_id     integer;
  potatoe_id  integer;

  vitya_id    integer;
  petya_id    integer;
  margo_id    integer;

  manager_margo_id  integer;

  moscow_storage_id   integer;
  omsk_storage_id     integer;
  kostroma_storage_id integer;
  x5_storage_id       integer;

  x5_consumer_id     integer;
  milk_rock_order_id integer;

  arslanbek_id        integer;
  transfer_request_id integer;


  item_group item_group;
BEGIN
  moscow_id   := location_create('Moscow');
  omsk_id     := location_create('Omsk');
  kostroma_id := location_create('Kostroma');

  milk_id    := item_kind_create('Milk',    'milliliter');
  rock_id    := item_kind_create('Rock',    'gram');
  potatoe_id := item_kind_create('Potatoe', 'gram');

  vitya_id := user_create('vitya-smirnov', 'Vitya',     'Smirnov');
  petya_id := user_create('petya-ivanov',  'Petya',     'Ivanov');
  margo_id := user_create('margo-kuprina', 'Margarita', 'Kuprina');

  manager_margo_id := manager_assign(margo_id);

  moscow_storage_id := storage_create('Moscow Storage', moscow_id);
  PERFORM admin_assign(petya_id, moscow_storage_id);
  PERFORM cell_create(moscow_storage_id, rock_id, 10);

  omsk_storage_id := storage_create('Omsk Storage', omsk_id);
  PERFORM admin_assign(vitya_id, omsk_storage_id);
  PERFORM cell_create(omsk_storage_id, potatoe_id, 20);
  PERFORM cell_create(omsk_storage_id, milk_id, 40);

  kostroma_storage_id := storage_create('Kostroma Storage', kostroma_id);

  x5_storage_id := storage_create('X5 Group Storage', moscow_id);
  PERFORM admin_assign(vitya_id, x5_storage_id);
  PERFORM cell_create(x5_storage_id, milk_id, 4000);
  PERFORM cell_create(x5_storage_id, rock_id, 100000000);

  x5_consumer_id := consumer_create('5ka Shops Net', x5_storage_id, 'He''s a strange guy.');
  milk_rock_order_id := order_create(x5_consumer_id, 'Need some milk and heavy rock!');
  PERFORM item_group_create(milk_rock_order_id, milk_id, 1000);
  PERFORM item_group_create(milk_rock_order_id, rock_id, 100000000);

  arslanbek_id := transporter_create('Arlanbek', 'Yandex Taxi Rating 5.0');

  transfer_request_id := transfer_request_create(
    manager_margo_id,   -- initiator
    moscow_storage_id,  -- src
    x5_storage_id,      -- dst
    arslanbek_id,       -- transporter
    '2011-01-01'::date, -- begin
    '2 hours'::interval -- duration
  );

  FOR item_group IN SELECT * FROM item_group 
  LOOP
    PERFORM transfer_group_create(transfer_request_id, item_group.id);
  END LOOP;

  RETURN;
END
$$ LANGUAGE plpgsql;

SELECT fill();
