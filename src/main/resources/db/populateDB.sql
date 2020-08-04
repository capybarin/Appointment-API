INSERT INTO role(name) VALUES ('ROLE_STUDENT');
INSERT INTO role(name) VALUES ('ROLE_TEACHER');

INSERT INTO users(firstName, lastName, role_id, email, password) VALUES ('Vlad', 'Bezd', 1, 'vladibzd@gmail.com', 'password');
INSERT INTO users(firstName, lastName, role_id, email, password) VALUES ('Teacher', 'Test', 2, 'testmusor06@gmail.com', 'password');


INSERT INTO status(name) VALUES ('Approved');
INSERT INTO status(name) VALUES ('Declined');
INSERT INTO status(name) VALUES ('Negotiation');