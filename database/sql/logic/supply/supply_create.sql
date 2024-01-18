CREATE FUNCTION supply_create(
  target_id integer,
  moment    timestamp
) RETURNS integer AS $$
DECLARE 
  supply_id integer;
BEGIN
  INSERT INTO supply (target_id, moment) 
  VALUES (target_id, moment)
  RETURNING id INTO STRICT supply_id;

  RETURN supply_id;
END;
$$ LANGUAGE plpgsql;