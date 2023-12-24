CREATE FUNCTION transfer_integrity_check()
RETURNS trigger AS $transfer_integrity_check$
DECLARE
  transfer_id  integer;
  item_kind_id integer;
  amount       positive_int;

  source_balance integer;

  source_id   integer;
  target_id   integer;
BEGIN
  transfer_id  := NEW.transfer_id;
  item_kind_id := NEW.item_kind_id;
  amount       := NEW.amount;

  SELECT src_storage_id, dst_storage_id 
  INTO source_id, target_id
  FROM transfer
  WHERE transfer.id = transfer_id;

  source_balance := storage_kind_balance(source_id, item_kind_id);

  IF source_balance < amount THEN
    RAISE EXCEPTION
      'can''t withdraw % units '
      'of type with id % '
      'from storage with id % '
      'with balance %'
    , amount, item_kind_id, source_id, source_balance;
  END IF;

  RETURN NEW;
END;
$transfer_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER transfer_integrity_check
BEFORE INSERT OR UPDATE ON transfer_atom
FOR EACH ROW EXECUTE FUNCTION transfer_integrity_check();
