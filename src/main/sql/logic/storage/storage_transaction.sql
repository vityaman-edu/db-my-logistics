CREATE FUNCTION transfer_is_commited (
  transfr transfer
) RETURNS boolean AS $$
  SELECT
    (transfr.source_approver_id IS NOT NULL) AND 
    (transfr.target_approver_id IS NOT NULL);
$$ LANGUAGE SQL;

CREATE VIEW storage_transaction_supply_income AS
  SELECT 
    supply.moment             AS moment,
    supply.target_id          AS storage_id, 
    supply_atom.item_kind_id  AS item_kind_id, 
    supply_atom.amount        AS amount
  FROM supply
  JOIN supply_atom ON supply_atom.supply_id = supply.id;

CREATE VIEW storage_transaction_transfer_income AS
  SELECT 
    transfer.withdraw_moment + transfer.duration AS moment,
    transfer.target_id                           AS storage_id, 
    transfer_atom.item_kind_id                   AS item_kind_id, 
    transfer_atom.amount                         AS amount
  FROM transfer
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  WHERE transfer_is_commited(transfer);

CREATE VIEW storage_transaction_transfer_withdraw AS
  SELECT 
    transfer.withdraw_moment    AS moment,
    transfer.source_id          AS storage_id,
    transfer_atom.item_kind_id  AS item_kind_id,
    -transfer_atom.amount       AS amount
  FROM transfer
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  WHERE transfer_is_commited(transfer);

CREATE VIEW storage_transaction_consume_withdraw AS
  SELECT 
    consume.moment             AS moment,
    consume.source_id          AS storage_id,
    consume_atom.item_kind_id  AS item_kind_id,
    -consume_atom.amount       AS amount
  FROM consume
  JOIN consume_atom ON consume_atom.consume_id = consume.id;

CREATE VIEW storage_transaction AS
  SELECT * 
  FROM (
    (SELECT * FROM storage_transaction_supply_income) UNION ALL 
    (SELECT * FROM storage_transaction_transfer_income) UNION ALL 
    (SELECT * FROM storage_transaction_transfer_withdraw) UNION ALL 
    (SELECT * FROM storage_transaction_consume_withdraw)
  );
