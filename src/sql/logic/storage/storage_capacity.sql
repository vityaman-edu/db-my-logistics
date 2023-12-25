CREATE VIEW storage_capacity_total AS
  SELECT storage_id, item_kind_id, SUM(capacity) as capacity
  FROM storage_cell
  GROUP BY storage_id, item_kind_id;

CREATE FUNCTION storage_capacity_occupied (
  moment timestamp
) RETURNS TABLE (
  storage_id   integer,
  item_kind_id integer, 
  capacity     integer
) AS $$
  SELECT storage_id, item_kind_id, -amount as capacity
  FROM storage_balance(moment);
$$ LANGUAGE SQL;

CREATE FUNCTION storage_capacity_free (
  moment timestamp
) RETURNS TABLE (
  storage_id   integer,
  item_kind_id integer, 
  capacity     integer
) AS $$
  SELECT storage_id, item_kind_id, SUM(capacity) as capacity
  FROM (
    (SELECT * FROM storage_capacity_total) UNION
    (SELECT * FROM storage_capacity_occupied(moment)))
  GROUP BY storage_id, item_kind_id;
$$ LANGUAGE SQL;

CREATE FUNCTION storage_kind_capacity_free (
  storage_id   integer,
  moment       timestamp,
  item_kind_id integer
) RETURNS integer AS $$
DECLARE 
  free_capacity integer;
BEGIN
  SELECT capacity INTO free_capacity
  FROM storage_capacity_free(moment)
  WHERE storage_capacity_free.storage_id = storage_kind_capacity_free.storage_id
    AND storage_capacity_free.item_kind_id = storage_kind_capacity_free.item_kind_id;

  RETURN free_capacity;
END;
$$ LANGUAGE plpgsql;