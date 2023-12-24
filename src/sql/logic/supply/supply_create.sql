CREATE FUNCTION supply_create(
  dst_storage_id integer
) RETURNS integer AS $$
DECLARE 
  supply_id integer;
BEGIN
  INSERT INTO supply (dst_storage_id) 
  VALUES (dst_storage_id)
  RETURNING id INTO STRICT supply_id;

  RETURN supply_id;
END;
$$ LANGUAGE plpgsql;