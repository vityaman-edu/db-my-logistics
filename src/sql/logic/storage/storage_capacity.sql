CREATE VIEW storage_capacity AS
  SELECT storage_id, item_kind_id, SUM(capacity) as capacity
  FROM storage_cell
  GROUP BY storage_id, item_kind_id
  ORDER BY storage_id;
