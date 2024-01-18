CREATE FUNCTION user_create(
  nickname   nickname,
  first_name person_name,
  last_name person_name
) RETURNS int AS $$
DECLARE 
  user_id integer;
BEGIN
  INSERT INTO users (nickname, first_name, last_name) 
  VALUES (nickname, first_name, last_name)
  RETURNING id INTO STRICT user_id;
  
  RETURN user_id;
END;
$$ LANGUAGE plpgsql;