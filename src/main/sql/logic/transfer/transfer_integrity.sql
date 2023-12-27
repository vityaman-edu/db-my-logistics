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

  PERFORM storage_transaction_validate(
    withdraw_moment, source_id, item_kind_id, -amount);

  PERFORM storage_transaction_validate(
    income_moment, target_id, item_kind_id, amount);

  RETURN NEW;
END;
$transfer_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER transfer_integrity_check
BEFORE INSERT OR UPDATE ON transfer_atom
FOR EACH ROW EXECUTE FUNCTION transfer_integrity_check();
