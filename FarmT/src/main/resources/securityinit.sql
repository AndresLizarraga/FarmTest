INSERT INTO user (id,enabled,password,username) 
VALUES(1,true,'$2a$04$BciG9EY0lLHdJ5ieajSoTelaDieLYce/iwpidOe88.87SQg1Gefia','admin');

INSERT INTO user (id,enabled,password,username) 
VALUES(2,true,'$2a$04$B0cCIQyx2qSfChWkchWqNe3wUcST3QQBE4mILvfwx1XT5j5tyR6Jm','user');

INSERT INTO user (id, enabled, password, username)
VALUES (3, true, '$2a$04$r9QAn6Usp021nZikvbJ3kOibOVhHx65M1CokndybPg6e73A2RKx0y', 'actuator');

INSERT INTO authority (id,authority) VALUES (1,'ROLE_ADMIN');
INSERT INTO authority (id,authority) VALUES (2,'ROLE_USER');
INSERT INTO authority (id,authority) VALUES (3,'ROLE_ACTUATOR');

INSERT INTO authorities_users (usuario_id, authority_id) VALUES (1,1);
INSERT INTO authorities_users (usuario_id, authority_id) VALUES (1,2);
INSERT INTO authorities_users (usuario_id, authority_id) VALUES (2,2);
INSERT INTO authorities_users (usuario_id, authority_id) VALUES (3,3);