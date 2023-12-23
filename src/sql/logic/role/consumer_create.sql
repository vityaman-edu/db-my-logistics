CREATE FUNCTION consumer_create(
  name       consumer_name,
  storage_id integer,
  note       text
) RETURNS integer AS $$
DECLARE 
  consumer_id integer;
BEGIN
  INSERT INTO consumer (name, note, storage_id)
  VALUES (name, note, storage_id)
  RETURNING id INTO STRICT consumer_id;
  
  RETURN consumer_id;
END;
$$ LANGUAGE plpgsql;