INSERT INTO role(name) VALUES (1, 'STUDENT');
INSERT INTO role(name) VALUES (2, 'TEACHER');

INSERT INTO users(firstName, lastName, role_id, email, password) VALUES ('Vlad', 'Bezd', 1, 'vladibzd@gmail.com', 'password');
INSERT INTO users(firstName, lastName, role_id, email, password) VALUES ('Teacher', 'Test', 2, 'testmusor06@gmail.com', 'password');


INSERT INTO status(name) VALUES (1, 'Approved');
INSERT INTO status(name) VALUES (2, 'Declined');
INSERT INTO status(name) VALUES (3, 'Negotiation');
INSERT INTO status(name) VALUES (4, 'Open');