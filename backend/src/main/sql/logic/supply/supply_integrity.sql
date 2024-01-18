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

  PERFORM storage_transaction_validate(
    income_moment, target_id, item_kind_id, amount);

  RETURN NEW;
END;
$supply_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER supply_integrity_check
BEFORE INSERT ON supply_atom
FOR EACH ROW EXECUTE FUNCTION supply_integrity_check();
