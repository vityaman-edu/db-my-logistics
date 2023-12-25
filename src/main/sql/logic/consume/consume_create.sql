CREATE FUNCTION consume_create(
  source_id integer,
  moment    timestamp
) RETURNS integer AS $$
DECLARE 
  consume_id integer;
BEGIN
  INSERT INTO consume (source_id, moment) 
  VALUES (source_id, moment)
  RETURNING id INTO STRICT consume_id;

  RETURN consume_id;
END;
$$ LANGUAGE plpgsql;