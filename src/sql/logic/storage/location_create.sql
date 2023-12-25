CREATE FUNCTION location_create(
  name location_name
) RETURNS int AS $$
DECLARE 
  location_id int;
BEGIN
  INSERT INTO location (name) 
  VALUES (name)
  RETURNING id INTO STRICT location_id;
  
  RETURN location_id;
END;
$$ LANGUAGE plpgsql;