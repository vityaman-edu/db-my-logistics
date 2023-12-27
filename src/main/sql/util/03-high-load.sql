CREATE FUNCTION high_load() RETURNS void AS $$
DECLARE 
  location_id integer;
  dollar_id   integer;
  ruble_id    integer;
  tester_id   integer;
  a_id        integer;
  b_id        integer;
  transfer_id integer;
  current     timestamp;
BEGIN
  location_id := location_create('High Load City');
  dollar_id   := item_kind_create('Dollar', 'Dollars');
  ruble_id    := item_kind_create('Ruble', 'Rubles');
  tester_id   := user_create('tester', 'Test', 'Testov');

  a_id := storage_create('AAAAAAA', location_id);
  PERFORM storage_cell_create(a_id, dollar_id, 100000000);
  PERFORM storage_cell_create(a_id, ruble_id, 100000000);

  b_id := storage_create('BBBBBBB', location_id);
  PERFORM storage_cell_create(b_id, dollar_id, 100000000);
  PERFORM storage_cell_create(b_id, ruble_id, 100000000);

  PERFORM manager_assign(tester_id);
  PERFORM admin_assign(tester_id, a_id);
  PERFORM admin_assign(tester_id, b_id);

  current := '2023-01-01 00:00'::timestamp;

  RAISE NOTICE 'Staring to do high load:';

  FOR i IN 1..1000 LOOP
    current := current + '1 minute'::interval;
    transfer_id := supply_create(a_id, current);
    PERFORM supply_atom_create(transfer_id, dollar_id, 2);
    PERFORM supply_atom_create(transfer_id, ruble_id, 2);

    current := current + '1 minute'::interval;
    transfer_id := supply_create(b_id, current);
    PERFORM supply_atom_create(transfer_id, dollar_id, 1);
    PERFORM supply_atom_create(transfer_id, ruble_id, 1);
  END LOOP;

  RAISE NOTICE 'Loop 1 is done.';

  FOR i IN 1..1000 LOOP
    current := current + '1 minute'::interval;
    transfer_id := transfer_create(
      a_id, b_id, current, current + '1 minute'::interval, tester_id);
    PERFORM transfer_atom_create(transfer_id, dollar_id, 1);  
    PERFORM transfer_atom_create(transfer_id, ruble_id, 1);  
    PERFORM transfer_approve(transfer_id, tester_id);
  END LOOP;

  RAISE NOTICE 'Loop 2 is done.';

  FOR i IN 1..1000 LOOP
    current := current + '1 minute'::interval;
    transfer_id := transfer_create(
      b_id, a_id, current, current + '1 minute'::interval, tester_id);
    PERFORM transfer_atom_create(transfer_id, dollar_id, 1);  
    PERFORM transfer_atom_create(transfer_id, ruble_id, 1);  
    PERFORM transfer_approve(transfer_id, tester_id);
  END LOOP;

  RAISE NOTICE 'Loop 3 is done.';

  RETURN;
END
$$ LANGUAGE plpgsql;
