

INSERT INTO USERS(id, name, password, email, role) VALUES(1, 'A', 'A', 'example1.com', 'ADMIN');
INSERT INTO USERS(id, name, password, email, role) VALUES(2, 'B', 'B', 'example2.com', 'USER');
INSERT INTO USERS(id, name, password, email, role) VALUES(3, 'C', 'C', 'example3.com', 'USER');
INSERT INTO USERS(id, name, password, email, role) VALUES(4, 'D', 'D', 'example4.com', 'USER');
INSERT INTO USERS(id, name, password, email, role) VALUES(5, 'E', 'E', 'example5.com', 'USER');

INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(1, 'asdas', 2);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(2, 'asdasdas', 2);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(3, 'dfgdfg', 4);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(4, 'werwer', 5);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(6, 'utzutz', 3);

INSERT INTO RECIPES(id, description, title) VALUES(1, 'description1', 'cake');
INSERT INTO RECIPES(id, description, title) VALUES(2, 'description2', 'pulled pork');
INSERT INTO RECIPES(id, description, title) VALUES(3, 'description3', 'soup');
INSERT INTO RECIPES(id, description, title) VALUES(4, 'description4', 'beef mince');

INSERT INTO COOK_BOOKS_RECIPES VALUES(1, 1);
INSERT INTO COOK_BOOKS_RECIPES VALUES(2, 2);
INSERT INTO COOK_BOOKS_RECIPES VALUES(3, 3);
INSERT INTO COOK_BOOKS_RECIPES VALUES(4, 4);