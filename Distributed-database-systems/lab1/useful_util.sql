select *
from pg_catalog.pg_constraint;

SELECT table_schema, table_name, column_name FROM information_schema.columns
WHERE table_name = 'test_table_1' and is_nullable = 'NO';

select *
from pg_catalog.pg_attribute
where attrelid = 17432;

select *
from pg_catalog.pg_class;

select *
from information_schema.tables;

select *
from information_schema.columns;