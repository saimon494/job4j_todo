create table item
(
    id          serial primary key,
    description varchar(50),
    created     timestamp,
    done        boolean,
    user_id     integer references i_user(id)
);

create table i_user
(
    id       serial primary key,
    name     varchar(50),
    email    varchar(50) unique,
    password varchar(50)
);

--truncate table item restart identity;
--truncate table i_user restart identity cascade;
