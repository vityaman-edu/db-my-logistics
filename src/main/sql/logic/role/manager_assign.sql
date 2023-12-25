CREATE FUNCTION manager_assign(
  user_id integer
) RETURNS integer AS $$
DECLARE 
  manager_id integer;
BEGIN
  INSERT INTO manager (user_id) 
  VALUES (user_id)
  RETURNING id INTO STRICT manager_id;
  
  RETURN manager_id;
END;
$$ LANGUAGE plpgsql;