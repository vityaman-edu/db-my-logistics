CREATE FUNCTION transfer_create(
  manager_id          integer,
  src_storage_id      integer,
  dst_storage_id      integer
) RETURNS integer AS $$
DECLARE 
  transfer_id integer;
BEGIN
  INSERT INTO transfer (
    manager_id,
    src_storage_id, 
    dst_storage_id
  ) VALUES (
    manager_id,
    src_storage_id, 
    dst_storage_id
  ) RETURNING id INTO STRICT transfer_id;
  
  RETURN transfer_id;
END;
$$ LANGUAGE plpgsql;