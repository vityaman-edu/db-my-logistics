CREATE FUNCTION transfer_create(
  source_id       integer,
  target_id       integer,
  withdraw_moment timestamp,
  income_moment   timestamp,
  initiator_id    integer
) RETURNS integer AS $$
DECLARE 
  manager_id  integer;
  transfer_id integer;
BEGIN
  SELECT id INTO STRICT manager_id
  FROM manager 
  WHERE manager.user_id = initiator_id;

  INSERT INTO transfer (
    source_id, 
    target_id,
    withdraw_moment,
    income_moment,
    initiator_id
  ) VALUES (
    source_id, 
    target_id,
    withdraw_moment,
    income_moment,
    manager_id
  ) RETURNING id INTO STRICT transfer_id;
  
  RETURN transfer_id;
END;
$$ LANGUAGE plpgsql;