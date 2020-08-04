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


CREATE TABLE IF NOT EXISTS teacher_data
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    date  DATE NOT NULL ,
    timeStart  TIME NOT NULL ,
    timeEnd  TIME NOT NULL ,
    workTime INTEGER NOT NULL ,
    workPrice FLOAT NOT NULL ,
    currency VARCHAR(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS appoint
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    date  DATE NOT NULL ,
    timeStart  TIME NOT NULL ,
    timeEnd  TIME NOT NULL ,
    status_id INTEGER NOT NULL ,
    price VARCHAR(15) NOT NULL
);
