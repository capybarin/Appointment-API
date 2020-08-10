create table role
(
    id   bigserial   not null
        constraint role_pkey
            primary key,
    name varchar(20) not null
);

create table users
(
    id        bigserial    not null
        constraint users_pkey
            primary key,
    firstname varchar(40)  not null,
    lastname  varchar(40)  not null,
    role_id   integer      not null,
    email     varchar(254) not null,
    password  varchar(35)  not null
);

create unique index user_email_uindex
    on users (email);


create table status
(
    id   bigserial   not null
        constraint status_pkey
            primary key,
    name varchar(20) not null
);

create table appoint
(
    id              bigserial not null
        constraint appoint_pkey
            primary key,
    status_id       integer   not null,
    student_id      integer,
    teacher_data_id integer   not null
);

create table teacher_data
(
    id         bigserial  not null
        constraint teacher_data_pk
            primary key,
    workfrom   time       not null,
    workto     time       not null,
    currency   varchar(3) not null,
    price      varchar(8) not null,
    teacher_id integer    not null,
    date       date       not null
);
