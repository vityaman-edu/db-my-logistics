CREATE FUNCTION transfer_create(
  src_storage_id      integer,
  dst_storage_id      integer
) RETURNS integer AS $$
DECLARE 
  transfer_id integer;
BEGIN
  INSERT INTO transfer (
    src_storage_id, 
    dst_storage_id
  ) VALUES (
    src_storage_id, 
    dst_storage_id
  ) RETURNING id INTO STRICT transfer_id;
  
  RETURN transfer_id;
END;
$$ LANGUAGE plpgsql;