CREATE FUNCTION item_group_create(
  order_id integer, 
  item_kind_id integer, 
  amount positive_int
) RETURNS integer AS $$
DECLARE 
  item_group_id integer;
BEGIN
  INSERT INTO item_group (order_id, item_kind_id, amount)
  VALUES (order_id, item_kind_id, amount)
  RETURNING id INTO STRICT item_group_id;
  
  RETURN item_group_id;
END;
$$ LANGUAGE plpgsql;