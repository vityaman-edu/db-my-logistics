CREATE FUNCTION transfer_create(
  source_id     integer,
  target_id     integer,
  withdraw_moment timestamp,
  duration      interval
) RETURNS integer AS $$
DECLARE 
  transfer_id integer;
BEGIN
  INSERT INTO transfer (
    source_id, 
    target_id,
    withdraw_moment,
    duration
  ) VALUES (
    source_id, 
    target_id,
    withdraw_moment,
    duration
  ) RETURNING id INTO STRICT transfer_id;
  
  RETURN transfer_id;
END;
$$ LANGUAGE plpgsql;