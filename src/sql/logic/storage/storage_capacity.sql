CREATE VIEW storage_capacity AS
  SELECT storage_id, item_kind_id, SUM(capacity) as capacity
  FROM storage_cell
  GROUP BY storage_id, item_kind_id;

CREATE VIEW storage_full_capacity AS
  SELECT storage_id, item_kind_id, -amount as capacity
  FROM storage_balance;

CREATE VIEW storage_free_capacity AS 
  SELECT storage_id, item_kind_id, SUM(COALESCE(capacity, 0)) as capacity
  FROM (
    (SELECT * FROM storage_capacity) UNION
    (SELECT * FROM storage_full_capacity)
  )
  GROUP BY storage_id, item_kind_id;
