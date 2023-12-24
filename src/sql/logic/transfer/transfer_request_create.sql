CREATE FUNCTION transfer_request_create(
  manager_id          integer,
  src_storage_id      integer,
  dst_storage_id      integer
) RETURNS integer AS $$
DECLARE 
  transfer_request_id integer;
BEGIN
  INSERT INTO transfer_request (
    manager_id,
    src_storage_id, 
    dst_storage_id
  ) VALUES (
    manager_id,
    src_storage_id, 
    dst_storage_id
  ) RETURNING id INTO STRICT transfer_request_id;
  
  RETURN transfer_request_id;
END;
$$ LANGUAGE plpgsql;