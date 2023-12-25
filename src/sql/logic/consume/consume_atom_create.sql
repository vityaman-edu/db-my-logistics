CREATE FUNCTION consume_atom_create(
  consume_id   integer,
  item_kind_id integer,
  amount       positive_int
) RETURNS integer AS $$
DECLARE 
  consume_atom_id integer;
BEGIN
  INSERT INTO consume_atom (consume_id, item_kind_id, amount)
  VALUES (consume_id, item_kind_id, amount)
  RETURNING id INTO STRICT consume_atom_id;

  RETURN consume_atom_id;
END;
$$ LANGUAGE plpgsql;