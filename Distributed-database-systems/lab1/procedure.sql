CREATE OR REPLACE PROCEDURE drop_all_not_null_constraints(schema_name VARCHAR(255))
AS
$$
DECLARE
    global_counter integer = 0;
    _table_name    varchar;
    _column_name   varchar;
BEGIN
    FOR _table_name IN (SELECT table_name FROM information_schema.tables WHERE table_schema = schema_name)
        LOOP
            FOR _column_name IN
                SELECT attname
                FROM pg_catalog.pg_attribute a
                WHERE attrelid =
                      (SELECT DISTINCT oid FROM pg_catalog.pg_class WHERE relname = _table_name) -- get table id
                  AND attnotnull                                                                 -- only NOT NULL columns
                  AND NOT attisdropped                                                           -- exclude dropped columns
                  AND attnum > 0                                                                 -- exclude system columns
                  AND NOT EXISTS( -- exclude PK columns
                        SELECT
                        FROM pg_catalog.pg_constraint c
                        WHERE c.conrelid = a.attrelid
                          AND c.contype = 'p' -- PRIMARY KEY
                          AND a.attnum = ANY (c.conkey)
                    )
                LOOP
                    global_counter = global_counter + 1;
                    EXECUTE concat_ws(' '
                        , 'ALTER TABLE'
                        , concat(schema_name, '.', _table_name)
                        , 'ALTER COLUMN'
                        , _column_name
                        , 'DROP NOT NULL'
                        );

                END LOOP;
            RAISE NOTICE 'Отключены ограничения NOT NULL для таблицы: %', _table_name;
        END LOOP;
    RAISE NOTICE 'Схема: %', schema_name;
    RAISE NOTICE 'Ограничений целостности типа NOT NULL отключено: %', global_counter;
END
$$ LANGUAGE plpgsql;