CREATE FUNCTION admin_assign(
  user_id    integer,
  storage_id integer
) RETURNS integer AS $$
DECLARE 
  admin_id integer;
BEGIN
  INSERT INTO admin (user_id, storage_id)
  VALUES (user_id, storage_id)
  RETURNING id INTO STRICT admin_id;
  
  RETURN admin_id;
END;
$$ LANGUAGE plpgsql;