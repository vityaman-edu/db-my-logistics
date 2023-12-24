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

  supply_id     integer;
  transfer_id integer;
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
  PERFORM storage_cell_create(moscow_storage_id, potatoe_id, 200);
  PERFORM storage_cell_create(moscow_storage_id, rock_id, 10000);
  PERFORM storage_cell_create(moscow_storage_id, milk_id, 600);

  omsk_storage_id := storage_create('Omsk Storage', omsk_id);
  PERFORM admin_assign(vitya_id, omsk_storage_id);
  PERFORM storage_cell_create(omsk_storage_id, potatoe_id, 20);
  PERFORM storage_cell_create(omsk_storage_id, rock_id, 900);
  PERFORM storage_cell_create(omsk_storage_id, milk_id, 40);

  kostroma_storage_id := storage_create('Kostroma Storage', kostroma_id);

  x5_storage_id := storage_create('X5 Group Storage', moscow_id);
  PERFORM admin_assign(vitya_id, x5_storage_id);
  PERFORM storage_cell_create(x5_storage_id, potatoe_id, 120);
  PERFORM storage_cell_create(x5_storage_id, milk_id, 4000);
  PERFORM storage_cell_create(x5_storage_id, rock_id, 1444);
  PERFORM storage_cell_create(x5_storage_id, rock_id, 100);
  -- Comment the next line to produce capacity integrity error
  PERFORM storage_cell_create(x5_storage_id, rock_id, 600);

  supply_id := supply_create(moscow_storage_id);
  PERFORM supply_atom_create(supply_id, milk_id, 1555);
  PERFORM supply_atom_create(supply_id, rock_id, 8880);

  supply_id := supply_create(x5_storage_id);
  PERFORM supply_atom_create(supply_id, potatoe_id, 211);
  PERFORM supply_atom_create(supply_id, rock_id, 1000);

  supply_id := supply_create(omsk_storage_id);
  PERFORM supply_atom_create(supply_id, milk_id, 700);
  PERFORM supply_atom_create(supply_id, rock_id, 999);

  transfer_id := transfer_create(moscow_storage_id, x5_storage_id);
  PERFORM transfer_atom_create(transfer_id, milk_id, 1000);
  PERFORM transfer_atom_create(transfer_id, rock_id, 1000);

  transfer_id := transfer_create(x5_storage_id, moscow_storage_id);
  PERFORM transfer_atom_create(transfer_id, potatoe_id, 100);
  PERFORM transfer_atom_create(transfer_id, rock_id, 888);

  transfer_id := transfer_create(omsk_storage_id, x5_storage_id);
  PERFORM transfer_atom_create(transfer_id, milk_id, 666);
  -- Uncomment the next line to produce balance integrity error
  -- PERFORM transfer_atom_create(transfer_id, milk_id, 100);
  PERFORM transfer_atom_create(transfer_id, rock_id, 111);

  RETURN;
END
$$ LANGUAGE plpgsql;

SELECT fill();
