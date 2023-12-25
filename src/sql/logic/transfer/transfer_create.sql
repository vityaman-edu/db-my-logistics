CREATE FUNCTION transfer_create(
  source_id integer,
  target_id integer
) RETURNS integer AS $$
DECLARE 
  transfer_id integer;
BEGIN
  INSERT INTO transfer (
    source_id, 
    target_id
  ) VALUES (
    source_id, 
    target_id
  ) RETURNING id INTO STRICT transfer_id;
  
  RETURN transfer_id;
END;
$$ LANGUAGE plpgsql;