CREATE FUNCTION consume_integrity_check()
RETURNS trigger AS $consume_integrity_check$
DECLARE
  consume_id   integer;
  item_kind_id integer;
  amount       positive_int;

  source_balance  integer;

  source_id       integer;
  withdraw_moment timestamp;
BEGIN
  consume_id   := NEW.consume_id;
  item_kind_id := NEW.item_kind_id;
  amount       := NEW.amount;

  SELECT 
    consume.source_id,
    consume.moment
  INTO 
    source_id, 
    withdraw_moment
  FROM consume
  WHERE consume.id = consume_id;

  source_balance := 
    storage_kind_balance(source_id, withdraw_moment, item_kind_id);

  IF source_balance < amount THEN
    RAISE EXCEPTION
      'can withdraw % units '
      'of type with id % '
      'from storage with id % '
      'with balance % '
      'on %'
    , amount, item_kind_id, source_id, source_balance, withdraw_moment;
  END IF;

  RETURN NEW;
END;
$consume_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER consume_integrity_check
BEFORE INSERT ON consume_atom
FOR EACH ROW EXECUTE FUNCTION consume_integrity_check();
