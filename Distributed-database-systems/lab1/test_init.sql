CREATE SCHEMA if not exists testing;
create table if not exists testing.test_table_1 (
        id bigserial primary key,
        nullable_1 varchar(255),
        not_null_1 varchar(255) not null,
        not_null_2 bigint not null,
        not_null_3 float not null
);
insert into testing.test_table_1 (nullable_1, not_null_1, not_null_2, not_null_3) values
      (null, 'a', 2, 2.3),
      ('b', 'c', 3, 4.5);