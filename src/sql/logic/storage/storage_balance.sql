CREATE VIEW storage_income AS
  SELECT
    storage.id AS storage_id, 
    transfer_atom.item_kind_id AS item_kind_id, 
    SUM(amount) AS amount
  FROM transfer
  JOIN storage ON storage.id = transfer.dst_storage_id
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  GROUP BY storage.id, transfer_atom.item_kind_id
  ORDER BY storage.id;

CREATE VIEW storage_outcome AS 
  SELECT
      storage.id AS storage_id, 
      transfer_atom.item_kind_id AS item_kind_id, 
      -SUM(amount) AS amount
  FROM transfer
  JOIN storage ON storage.id = transfer.src_storage_id
  JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
  GROUP BY storage.id, transfer_atom.item_kind_id
  ORDER BY storage.id;

CREATE VIEW storage_balance AS 
  SELECT storage_id, item_kind_id, SUM(amount) AS amount
  FROM (
    (SELECT * FROM storage_income) UNION 
    (SELECT * FROM storage_outcome))
  GROUP BY storage_id, item_kind_id
  ORDER BY storage_id;
