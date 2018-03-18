CREATE TABLE users
(id VARCHAR(20) PRIMARY KEY,
 name VARCHAR(30),
 email VARCHAR(30),
 admin BOOLEAN,
 last_login TIMESTAMP,
 is_active BOOLEAN,
 pass VARCHAR(300));

INSERT INTO users (id, name, email, admin, is_active, pass) VALUES (1, 'Kevin', '12980829@qq.com', true, true, '123456');
