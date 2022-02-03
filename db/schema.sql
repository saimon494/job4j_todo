drop table if exists item cascade;
drop table if exists i_user cascade;

create table i_user
(
    id       serial primary key,
    name     varchar(50),
    email    varchar(50) unique,
    password varchar(50)
);
create table item
(
    id          serial primary key,
    description varchar(50),
    created     timestamp,
    done        boolean,
    user_id     integer references i_user (id)
);
create table category
(
    id   serial primary key,
    name text
);
insert into category(name) values ('Home');
insert into category(name) values ('Job');
insert into category(name) values ('Learning');
insert into category(name) values ('Family');