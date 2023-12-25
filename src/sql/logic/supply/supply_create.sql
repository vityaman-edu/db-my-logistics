CREATE FUNCTION supply_create(
  target_id integer
) RETURNS integer AS $$
DECLARE 
  supply_id integer;
BEGIN
  INSERT INTO supply (target_id) 
  VALUES (target_id)
  RETURNING id INTO STRICT supply_id;

  RETURN supply_id;
END;
$$ LANGUAGE plpgsql;