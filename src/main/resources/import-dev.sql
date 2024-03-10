INSERT INTO `user`(id, name, age) VALUES(100,'USER1',18);
INSERT INTO `user`(id, name, age) VALUES(101,'USER2',18);
INSERT INTO `user`(id, name, age) VALUES(102,'USER3',18);

INSERT INTO car(id, user_id, model) VALUES(100, 100,'Model A');
INSERT INTO car(id, user_id, model) VALUES(101, 100,'Model B');
INSERT INTO car(id, user_id, model) VALUES(102, 101,'Model A');
INSERT INTO car(id, user_id, model) VALUES(103, 101,'Model B');
INSERT INTO car(id, user_id, model) VALUES(105, 102,'Model A');
INSERT INTO car(id, user_id, model) VALUES(106, 102,'Model B');
INSERT INTO car(id, user_id, model) VALUES(107, 102,'Model D');