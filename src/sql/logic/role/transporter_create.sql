CREATE FUNCTION transporter_create(
  name transporter_name, 
  note text
) RETURNS integer AS $$
DECLARE 
  transporter_id integer;
BEGIN
  INSERT INTO transporter (name, note)
  VALUES (name, note)
  RETURNING id INTO STRICT transporter_id;
  
  RETURN transporter_id;
END;
$$ LANGUAGE plpgsql;