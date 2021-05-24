CREATE TABLE item
(
    id          serial primary key,
    description text,
    created     timestamp,
    done        boolean
);

--truncate table item restart identity;
