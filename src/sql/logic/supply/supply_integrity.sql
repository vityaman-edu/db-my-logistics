CREATE FUNCTION supply_integrity_check()
RETURNS trigger AS $supply_integrity_check$
DECLARE
  supply_id    integer;
  item_kind_id integer;
  amount       positive_int;

  target_capacity integer;

  target_id     integer;
  income_moment timestamp;
BEGIN
  supply_id    := NEW.supply_id;
  item_kind_id := NEW.item_kind_id;
  amount       := NEW.amount;

  SELECT 
    supply.target_id,
    supply.moment
  INTO 
    target_id, 
    income_moment
  FROM supply
  WHERE supply.id = supply_id;

  target_capacity := 
    storage_kind_capacity_free(target_id, income_moment, item_kind_id);

  IF target_capacity < amount THEN
    RAISE EXCEPTION
      'can income % units '
      'of type with id % '
      'to storage with id % '
      'with capacity % '
      'on %'
    , amount, item_kind_id, target_id, target_capacity, income_moment;
  END IF;

  RETURN NEW;
END;
$supply_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER supply_integrity_check
BEFORE INSERT ON supply_atom
FOR EACH ROW EXECUTE FUNCTION supply_integrity_check();
