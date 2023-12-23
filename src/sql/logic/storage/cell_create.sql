CREATE FUNCTION cell_create(
  storage_id   integer,
  item_kind_id integer,
  capacity     positive_int
) RETURNS integer AS $$
DECLARE 
  cell_id integer;
BEGIN
  INSERT INTO cell (storage_id, item_kind_id, capacity) 
  VALUES (storage_id, item_kind_id, capacity)
  RETURNING id INTO STRICT cell_id;
  
  RETURN cell_id;
END;
$$ LANGUAGE plpgsql;