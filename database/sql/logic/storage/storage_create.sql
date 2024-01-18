CREATE FUNCTION storage_create(
  name        storage_name,
  location_id integer
) RETURNS integer AS $$
DECLARE 
  storage_id integer;
BEGIN
  INSERT INTO storage (name, location_id) 
  VALUES (name, location_id)
  RETURNING id INTO STRICT storage_id;
  
  RETURN storage_id;
END;
$$ LANGUAGE plpgsql;