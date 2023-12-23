DROP DOMAIN IF EXISTS positive_int;
CREATE DOMAIN positive_int
AS int
CHECK (VALUE > 0);

DROP DOMAIN IF EXISTS nickname;
CREATE DOMAIN nickname 
AS varchar(31) 
CHECK (VALUE ~ '[a-z-]{3,31}');

DROP DOMAIN IF EXISTS person_name;
CREATE DOMAIN person_name 
AS varchar(31) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,31}');

DROP DOMAIN IF EXISTS location_name;
CREATE DOMAIN location_name 
AS varchar(63) 
CHECK (VALUE ~ '[a-zA-Z''-]{4,63}');

DROP DOMAIN IF EXISTS storage_name;
CREATE DOMAIN storage_name 
AS varchar(63) 
CHECK (VALUE ~ '[a-zA-Z''-]{5,63}');

DROP DOMAIN IF EXISTS item_kind_name;
CREATE DOMAIN item_kind_name 
AS varchar(127) 
CHECK (VALUE ~ '[a-zA-Z ''-]{3,127}');

DROP DOMAIN IF EXISTS item_kind_unit;
CREATE DOMAIN item_kind_unit
AS varchar(63) 
CHECK (VALUE ~ '[a-z.]{3,63}');

DROP DOMAIN IF EXISTS consumer_name;
CREATE DOMAIN consumer_name
AS varchar(127) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,127}');

DROP DOMAIN IF EXISTS transporter_name;
CREATE DOMAIN transporter_name
AS varchar(127) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,127}');
