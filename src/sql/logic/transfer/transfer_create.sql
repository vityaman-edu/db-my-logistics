CREATE FUNCTION transfer_create(
  source_id       integer,
  target_id       integer,
  withdraw_moment timestamp,
  duration        interval,
  initiator_id    integer
) RETURNS integer AS $$
DECLARE 
  transfer_id integer;
BEGIN
  INSERT INTO transfer (
    source_id, 
    target_id,
    withdraw_moment,
    duration,
    initiator_id
  ) VALUES (
    source_id, 
    target_id,
    withdraw_moment,
    duration,
    initiator_id
  ) RETURNING id INTO STRICT transfer_id;
  
  RETURN transfer_id;
END;
$$ LANGUAGE plpgsql;