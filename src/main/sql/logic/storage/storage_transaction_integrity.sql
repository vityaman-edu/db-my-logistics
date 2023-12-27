CREATE FUNCTION storage_balance_apply (
  balance       integer,
  moment        timestamp,
  storage_id    integer, 
  item_kind_id  integer, 
  amount        integer
) RETURNS integer AS $$
BEGIN
  IF balance + amount < 0 THEN
    RAISE EXCEPTION
      'can not withdraw % units '
      'of type with id % '
      'from storage with id % '
      'with balance % '
      'on %'
    , -amount, item_kind_id, storage_id, balance, moment;
  END IF;
  balance := balance + amount;
  RETURN balance;
END;
$$ LANGUAGE plpgsql;

CREATE FUNCTION storage_capacity_apply (
  capacity      integer,
  moment        timestamp,
  storage_id    integer, 
  item_kind_id  integer, 
  amount        integer
) RETURNS integer AS $$
BEGIN
  IF capacity - amount < 0 THEN
    RAISE EXCEPTION
      'can not income % units '
      'of type with id % '
      'to storage with id % '
      'with capacity % '
      'on %'
    , amount, item_kind_id, storage_id, capacity, moment;
  END IF;
  capacity := capacity - amount;
  RETURN capacity;
END;
$$ LANGUAGE plpgsql;

CREATE FUNCTION storage_transaction_validate (
  moment        timestamp,
  storage_id    integer, 
  item_kind_id  integer, 
  amount        integer
) RETURNS void AS $$
DECLARE
  balance     integer;
  capacity    integer;
  transact    RECORD;
BEGIN
  balance  := storage_kind_balance(storage_id, moment, item_kind_id);
  capacity := storage_kind_capacity_free(storage_id, moment, item_kind_id);

  balance := storage_balance_apply(balance, 
    moment, storage_id, item_kind_id, amount);
  capacity := storage_capacity_apply(capacity, 
    moment, storage_id, item_kind_id, amount);

  FOR transact IN 
    SELECT *
    FROM storage_transaction
    WHERE storage_transaction.moment > storage_transaction_validate.moment
      AND storage_transaction.storage_id = storage_transaction_validate.storage_id
      AND storage_transaction.item_kind_id = storage_transaction_validate.item_kind_id
  LOOP
    balance := storage_balance_apply(balance, 
      transact.moment, transact.storage_id, 
      item_kind_id, transact.amount);
    capacity := storage_capacity_apply(capacity, 
      transact.moment, transact.storage_id, 
      item_kind_id, transact.amount);
  END LOOP;
END;
$$ LANGUAGE plpgsql;