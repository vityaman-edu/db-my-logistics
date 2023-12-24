CREATE FUNCTION fill() RETURNS void AS $$
DECLARE 
  moscow_id           integer;
  omsk_id             integer;
  kostroma_id         integer;

  milk_id             integer;
  rock_id             integer;
  potatoe_id          integer;

  vitya_id            integer;
  petya_id            integer;
  margo_id            integer;

  manager_margo_id    integer;

  moscow_storage_id   integer;
  omsk_storage_id     integer;
  kostroma_storage_id integer;
  x5_storage_id       integer;

  transfer_request_1_id integer;
  transfer_request_2_id integer;
  transfer_request_3_id integer;
BEGIN
  moscow_id   := location_create('Moscow');
  omsk_id     := location_create('Omsk');
  kostroma_id := location_create('Kostroma');

  milk_id     := item_kind_create('Milk',    'milliliter');
  rock_id     := item_kind_create('Rock',    'gram');
  potatoe_id  := item_kind_create('Potatoe', 'gram');

  vitya_id := user_create('vitya-smirnov', 'Vitya',     'Smirnov');
  petya_id := user_create('petya-ivanov',  'Petya',     'Ivanov');
  margo_id := user_create('margo-kuprina', 'Margarita', 'Kuprina');

  manager_margo_id := manager_assign(margo_id);

  moscow_storage_id := storage_create('Moscow Storage', moscow_id);
  PERFORM admin_assign(petya_id, moscow_storage_id);
  PERFORM storage_cell_create(moscow_storage_id, rock_id, 10);

  omsk_storage_id := storage_create('Omsk Storage', omsk_id);
  PERFORM admin_assign(vitya_id, omsk_storage_id);
  PERFORM storage_cell_create(omsk_storage_id, potatoe_id, 20);
  PERFORM storage_cell_create(omsk_storage_id, milk_id, 40);

  kostroma_storage_id := storage_create('Kostroma Storage', kostroma_id);

  x5_storage_id := storage_create('X5 Group Storage', moscow_id);
  PERFORM admin_assign(vitya_id, x5_storage_id);
  PERFORM storage_cell_create(x5_storage_id, milk_id, 4000);
  PERFORM storage_cell_create(x5_storage_id, rock_id, 100000000);
  PERFORM storage_cell_create(x5_storage_id, rock_id, 1);

  transfer_request_1_id := transfer_request_create(
    manager_margo_id,
    moscow_storage_id,
    x5_storage_id
  );
  PERFORM transfer_atom_create(transfer_request_1_id, milk_id, 1000);
  PERFORM transfer_atom_create(transfer_request_1_id, rock_id, 100000000);

  transfer_request_2_id := transfer_request_create(
    manager_margo_id,
    x5_storage_id,
    moscow_storage_id
  );
  PERFORM transfer_atom_create(transfer_request_2_id, potatoe_id, 100);
  PERFORM transfer_atom_create(transfer_request_2_id, rock_id, 888);

  transfer_request_3_id := transfer_request_create(
    manager_margo_id,
    omsk_storage_id,
    x5_storage_id
  );
  PERFORM transfer_atom_create(transfer_request_3_id, milk_id, 666);
  PERFORM transfer_atom_create(transfer_request_3_id, rock_id, 111);

  RETURN;
END
$$ LANGUAGE plpgsql;

SELECT fill();
