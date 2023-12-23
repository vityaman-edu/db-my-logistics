CREATE FUNCTION transfer_request_create(
  manager_id          integer,
  src_storage_id      integer, 
  dst_storage_id      integer, 
  transporter_id      integer, 
  expected_begin_date date, 
  expected_duration   interval
) RETURNS integer AS $$
DECLARE 
  transfer_request_id integer;
BEGIN
  INSERT INTO transfer_request (
    manager_id,
    src_storage_id, 
    dst_storage_id, 
    transporter_id, 
    expected_begin_date, 
    expected_duration
  ) VALUES (
    manager_id,
    src_storage_id, 
    dst_storage_id, 
    transporter_id, 
    expected_begin_date, 
    expected_duration
  ) RETURNING id INTO STRICT transfer_request_id;
  
  RETURN transfer_request_id;
END;
$$ LANGUAGE plpgsql;