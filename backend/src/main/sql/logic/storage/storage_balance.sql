CREATE FUNCTION storage_balance (
  moment timestamp
) RETURNS TABLE (
  storage_id integer,
  item_kind_id integer, 
  amount       integer
) AS $$
  SELECT
    storage_transaction.storage_id AS storage_id,
    storage_transaction.item_kind_id AS item_kind_id,
    SUM(storage_transaction.amount) AS amount
  FROM storage_transaction
  WHERE storage_transaction.moment <= storage_balance.moment
  GROUP BY 
    storage_transaction.storage_id, 
    storage_transaction.item_kind_id;
$$ LANGUAGE SQL;

CREATE FUNCTION storage_kind_balance (
  storage_id   integer,
  moment       timestamp,
  item_kind_id integer
) RETURNS integer AS $$
DECLARE 
  balance integer;
BEGIN
  SELECT amount INTO balance
  FROM storage_balance(moment)
  WHERE storage_balance.storage_id = storage_kind_balance.storage_id
    AND storage_balance.item_kind_id = storage_kind_balance.item_kind_id;

  RETURN balance;
END;
$$ LANGUAGE plpgsql;
