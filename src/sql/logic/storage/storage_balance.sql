CREATE FUNCTION storage_transfer_income (
  moment timestamp
) RETURNS TABLE (
  storage_id   integer,
  item_kind_id integer, 
  amount       integer
) AS $$
  SELECT 
    transfer.target_id         AS storage_id,
    transfer_atom.item_kind_id AS item_kind_id, 
    SUM(transfer_atom.amount)  AS amount
  FROM transfer
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  WHERE transfer.withdraw_moment + transfer.duration <= moment
  GROUP BY transfer.target_id, item_kind_id;
$$ LANGUAGE SQL;

CREATE FUNCTION storage_supply_income (
  moment timestamp
) RETURNS TABLE (
  storage_id   integer,
  item_kind_id integer, 
  amount       integer
) AS $$
  SELECT 
    supply.target_id AS storage_id, 
    item_kind_id, 
    SUM(amount) AS amount
  FROM supply
  JOIN supply_atom ON supply_atom.supply_id = supply.id
  WHERE supply.moment <= moment
  GROUP BY supply.target_id, item_kind_id;
$$ LANGUAGE SQL;

CREATE FUNCTION storage_income (
  moment timestamp
) RETURNS TABLE (
  storage_id   integer,
  item_kind_id integer, 
  amount       integer
) AS $$
  SELECT storage_id, item_kind_id, SUM(amount) AS amount
  FROM (
    (SELECT * FROM storage_transfer_income(moment)) UNION 
    (SELECT * FROM storage_supply_income(moment)))
  GROUP BY storage_id, item_kind_id;
$$ LANGUAGE SQL;

CREATE FUNCTION storage_outcome (
  moment timestamp
) RETURNS TABLE (
  storage_id   integer,
  item_kind_id integer, 
  amount       integer
) AS $$
  SELECT
      transfer.source_id         AS storage_id,
      transfer_atom.item_kind_id AS item_kind_id, 
      -SUM(amount)               AS amount
  FROM transfer
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  WHERE transfer.withdraw_moment <= moment
  GROUP BY transfer.source_id, transfer_atom.item_kind_id;
$$ LANGUAGE SQL;

CREATE FUNCTION storage_balance (
  moment timestamp
) RETURNS TABLE (
  storage_id integer,
  item_kind_id integer, 
  amount       integer
) AS $$
  SELECT storage_id, item_kind_id, SUM(amount) AS amount
  FROM (
    (SELECT * FROM storage_income(moment)) UNION 
    (SELECT * FROM storage_outcome(moment)))
  GROUP BY storage_id, item_kind_id;
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
