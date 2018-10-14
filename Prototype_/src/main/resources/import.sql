INSERT INTO USERS(id, name, password, email, role) VALUES(1, 'A', '$2a$04$Ne29GbijkqkXujftzHR4A.D.bUST7deL..sGWj2wu1eaCnYrswxzm', 'example1.com', 'ROLE_ADMIN');
INSERT INTO USERS(id, name, password, email, role) VALUES(2, 'B', '$2a$04$oAypge2qHD.Fi61EftOSg.ayMHUDmsufBljz1V8DAsVJsm93Qhieq', 'example2.com', 'ROLE_USER');
INSERT INTO USERS(id, name, password, email, role) VALUES(3, 'C', '$2a$04$oAypge2qHD.Fi61EftOSg.ayMHUDmsufBljz1V8DAsVJsm93Qhieq', 'example3.com', 'ROLE_USER');
INSERT INTO USERS(id, name, password, email, role) VALUES(4, 'D', '$2a$04$oAypge2qHD.Fi61EftOSg.ayMHUDmsufBljz1V8DAsVJsm93Qhieq', 'example4.com', 'ROLE_USER');
INSERT INTO USERS(id, name, password, email, role) VALUES(5, 'E', '$2a$04$oAypge2qHD.Fi61EftOSg.ayMHUDmsufBljz1V8DAsVJsm93Qhieq', 'example5.com', 'ROLE_USER');

INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(1, 'Cakes', 2);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(2, 'French', 2);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(7, 'Meat', 2);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(3, 'Favorite', 4);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(8, 'WifeLikes', 4);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(4, 'New', 5);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(5, 'Old', 5);
INSERT INTO COOK_BOOKS(id, title, user_id) VALUES(6, 'Beginner', 3);

INSERT INTO RECIPES(id, description, title) VALUES(1, 'description1', 'cake1'); // 1
INSERT INTO RECIPES(id, description, title) VALUES(2, 'description2', 'pulled pork'); // 2
INSERT INTO RECIPES(id, description, title) VALUES(3, 'description3', 'soup1'); // 3
INSERT INTO RECIPES(id, description, title) VALUES(4, 'description4', 'beef1 mince'); // 8 
INSERT INTO RECIPES(id, description, title) VALUES(5, 'description1', 'cake2'); // 1 
INSERT INTO RECIPES(id, description, title) VALUES(6, 'description2', 'pulled2 pork'); // 2
INSERT INTO RECIPES(id, description, title) VALUES(7, 'description3', 'soup2'); // 3
INSERT INTO RECIPES(id, description, title) VALUES(8, 'description4', 'beef2 mince'); //4
INSERT INTO RECIPES(id, description, title) VALUES(9, 'description1', 'cake3'); // 8
INSERT INTO RECIPES(id, description, title) VALUES(10, 'description2', 'pulled3 pork'); // 7
INSERT INTO RECIPES(id, description, title) VALUES(11, 'description3', 'soup3'); // 11
INSERT INTO RECIPES(id, description, title) VALUES(12, 'description4', 'beef3 mince'); // 7

INSERT INTO COOK_BOOKS_RECIPES VALUES(1, 1);
INSERT INTO COOK_BOOKS_RECIPES VALUES(1, 5);

INSERT INTO COOK_BOOKS_RECIPES VALUES(2, 1);
INSERT INTO COOK_BOOKS_RECIPES VALUES(2, 2);
INSERT INTO COOK_BOOKS_RECIPES VALUES(2, 6);
INSERT INTO COOK_BOOKS_RECIPES VALUES(2, 10);

INSERT INTO COOK_BOOKS_RECIPES VALUES(3, 3);
INSERT INTO COOK_BOOKS_RECIPES VALUES(3, 7);

INSERT INTO COOK_BOOKS_RECIPES VALUES(4, 4);
INSERT INTO COOK_BOOKS_RECIPES VALUES(4, 8);

INSERT INTO COOK_BOOKS_RECIPES VALUES(7, 10);
INSERT INTO COOK_BOOKS_RECIPES VALUES(7, 12);

INSERT INTO COOK_BOOKS_RECIPES VALUES(8, 9);
INSERT INTO COOK_BOOKS_RECIPES VALUES(8, 11);