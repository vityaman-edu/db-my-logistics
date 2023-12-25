CREATE FUNCTION transfer_integrity_check()
RETURNS trigger AS $transfer_integrity_check$
DECLARE
  transfer_id  integer;
  item_kind_id integer;
  amount       positive_int;

  source_balance  integer;
  target_capacity integer;

  source_id     integer;
  target_id     integer;
  withdraw_moment timestamp;
  income_moment   timestamp;
BEGIN
  transfer_id  := NEW.transfer_id;
  item_kind_id := NEW.item_kind_id;
  amount       := NEW.amount;

  SELECT 
    transfer.source_id, 
    transfer.target_id,
    transfer.withdraw_moment,
    transfer.withdraw_moment + transfer.duration
  INTO 
    source_id, 
    target_id, 
    withdraw_moment, 
    income_moment
  FROM transfer
  WHERE transfer.id = transfer_id;

  source_balance  := 
    storage_kind_balance(source_id, withdraw_moment, item_kind_id);
  target_capacity := 
    storage_kind_capacity_free(target_id, income_moment, item_kind_id);

  IF source_balance < amount THEN
    RAISE EXCEPTION
      'can withdraw % units '
      'of type with id % '
      'from storage with id % '
      'with balance % '
      'on %'
    , amount, item_kind_id, source_id, source_balance, withdraw_moment;
  END IF;

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
$transfer_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER transfer_integrity_check
BEFORE INSERT ON transfer_atom
FOR EACH ROW EXECUTE FUNCTION transfer_integrity_check();
