CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL NOT NULL PRIMARY KEY ,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    firstName  VARCHAR(40) NOT NULL ,
    lastName  VARCHAR(40) NOT NULL ,
    role_id  INTEGER NOT NULL ,
    email VARCHAR(254) NOT NULL ,
    password VARCHAR(35)  NOT NULL
);

CREATE UNIQUE INDEX user_email_uindex ON users (email);


CREATE TABLE IF NOT EXISTS status
(
    id   BIGSERIAL NOT NULL PRIMARY KEY ,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS appoint
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    date  DATE NOT NULL ,
    timeStart  TIME NOT NULL ,
    timeEnd  TIME NOT NULL ,
    price VARCHAR(15) NOT NULL,
    teacher_data_id integer not null ,
    status_id INTEGER NOT NULL ,
    teacher_id INTEGER NOT NULL ,
    student_id INTEGER NOT NULL
);

CREATE TABLE teacher_data
(
    id bigserial not null,
    workFrom time not null,
    workTo time not null,
    currency varchar(3) not null,
    price varchar(8) not null,
    teacher_id int not null
);

create unique index teacher_data_id_uindex
    on teacher_data (id);

alter table teacher_data
    add constraint teacher_data_pk
        primary key (id);

