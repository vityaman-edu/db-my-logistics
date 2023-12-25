CREATE FUNCTION supply_atom_create(
  supply_id    integer,
  item_kind_id integer,
  amount       positive_int
) RETURNS integer AS $$
DECLARE 
  supply_atom_id integer;
BEGIN
  INSERT INTO supply_atom (supply_id, item_kind_id, amount)
  VALUES (supply_id, item_kind_id, amount)
  RETURNING id INTO STRICT supply_atom_id;

  RETURN supply_atom_id;
END;
$$ LANGUAGE plpgsql;