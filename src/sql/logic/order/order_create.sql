CREATE FUNCTION order_create(
  consumer_id integer,
  note        text
) RETURNS int AS $$
DECLARE 
  order_id int;
BEGIN
  INSERT INTO item_order (consumer_id, note)
  VALUES (consumer_id, note)
  RETURNING id INTO STRICT order_id;
  
  RETURN order_id;
END;
$$ LANGUAGE plpgsql;