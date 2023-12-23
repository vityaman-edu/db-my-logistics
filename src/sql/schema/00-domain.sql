CREATE DOMAIN positive_int
AS int
CHECK (VALUE > 0);

CREATE DOMAIN nickname 
AS varchar(31) 
CHECK (VALUE ~ '[a-z-]{3,31}');

CREATE DOMAIN person_name 
AS varchar(31) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,31}');

CREATE DOMAIN location_name 
AS varchar(63) 
CHECK (VALUE ~ '[a-zA-Z''-]{4,63}');

CREATE DOMAIN storage_name 
AS varchar(63) 
CHECK (VALUE ~ '[a-zA-Z''-]{5,63}');

CREATE DOMAIN item_kind_name 
AS varchar(127) 
CHECK (VALUE ~ '[a-zA-Z ''-]{3,127}');

CREATE DOMAIN item_kind_unit
AS varchar(63) 
CHECK (VALUE ~ '[a-z.]{3,63}');

CREATE DOMAIN consumer_name
AS varchar(127) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,127}');

CREATE DOMAIN transporter_name
AS varchar(127) 
CHECK (VALUE ~ '[a-zA-Z''-]{3,127}');
