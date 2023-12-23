CREATE FUNCTION item_kind_create(
  name item_kind_name,
  unit item_kind_unit
) RETURNS integer AS $$
DECLARE 
  item_kind_id integer;
BEGIN
  INSERT INTO item_kind (name, unit) 
  VALUES (name, unit)
  RETURNING id INTO STRICT item_kind_id;
  
  RETURN item_kind_id;
END;
$$ LANGUAGE plpgsql;