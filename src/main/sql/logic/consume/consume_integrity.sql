CREATE FUNCTION consume_integrity_check()
RETURNS trigger AS $consume_integrity_check$
DECLARE
  consume_id   integer;
  item_kind_id integer;
  amount       integer;

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

  PERFORM storage_transaction_validate(
    withdraw_moment, source_id, item_kind_id, -amount);

  RETURN NEW;
END;
$consume_integrity_check$ LANGUAGE plpgsql;

CREATE TRIGGER consume_integrity_check
BEFORE INSERT ON consume_atom
FOR EACH ROW EXECUTE FUNCTION consume_integrity_check();
