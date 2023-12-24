CREATE FUNCTION transfer_atom_create(
  transfer_request_id integer,
  item_kind_id        integer,
  amount              positive_int
) RETURNS integer AS $$
DECLARE 
  transfer_atom_id integer;
BEGIN
  INSERT INTO transfer_atom (transfer_request_id, item_kind_id, amount)
  VALUES (transfer_request_id, item_kind_id, amount)
  RETURNING id INTO STRICT transfer_atom_id;
  
  RETURN transfer_atom_id;
END;
$$ LANGUAGE plpgsql;