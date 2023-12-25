CREATE FUNCTION storage_cell_create(
  storage_id   integer,
  item_kind_id integer,
  capacity     positive_int
) RETURNS integer AS $$
DECLARE 
  storage_cell_id integer;
BEGIN
  INSERT INTO storage_cell (storage_id, item_kind_id, capacity) 
  VALUES (storage_id, item_kind_id, capacity)
  RETURNING id INTO STRICT storage_cell_id;
  
  RETURN storage_cell_id;
END;
$$ LANGUAGE plpgsql;