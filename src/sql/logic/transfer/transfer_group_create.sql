CREATE FUNCTION transfer_group_create(
  transfer_request_id integer,
  item_group_id       integer
) RETURNS void AS $$
BEGIN
  INSERT INTO transfer_group (transfer_request_id, item_group_id) 
  VALUES (transfer_request_id, item_group_id);
  
  RETURN;
END;
$$ LANGUAGE plpgsql;