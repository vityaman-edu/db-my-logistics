CREATE VIEW storage_transfer_income AS
  SELECT
    transfer.dst_storage_id    AS storage_id, 
    transfer_atom.item_kind_id AS item_kind_id, 
    SUM(amount)                AS amount
  FROM transfer
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  GROUP BY transfer.dst_storage_id, transfer_atom.item_kind_id;

CREATE VIEW storage_supply_income AS
  SELECT 
    supply.dst_storage_id    AS storage_id,
    supply_atom.item_kind_id AS item_kind_id,
    SUM(amount)              AS amount
  FROM supply
  JOIN supply_atom ON supply_atom.supply_id = supply.id
  GROUP BY supply.dst_storage_id, supply_atom.item_kind_id;

CREATE VIEW storage_income AS
  SELECT storage_id, item_kind_id, SUM(amount) AS amount
  FROM (
    (SELECT * FROM storage_transfer_income) UNION 
    (SELECT * FROM storage_supply_income))
  GROUP BY storage_id, item_kind_id;

CREATE VIEW storage_outcome AS 
  SELECT
      transfer.src_storage_id    AS storage_id, 
      transfer_atom.item_kind_id AS item_kind_id, 
      -SUM(amount)               AS amount
  FROM transfer
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  GROUP BY transfer.src_storage_id, transfer_atom.item_kind_id;

CREATE VIEW storage_balance AS 
  SELECT storage_id, item_kind_id, SUM(amount) AS amount
  FROM (
    (SELECT * FROM storage_income) UNION 
    (SELECT * FROM storage_outcome))
  GROUP BY storage_id, item_kind_id;
