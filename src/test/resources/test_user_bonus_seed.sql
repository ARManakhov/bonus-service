INSERT INTO user_t (id, password, role, username)
VALUES (1, '$2a$10$EgDjsKPvemdFsU.KwdGbFuE9jSvim2ptB8b.md7fUV7NLf7jMILJq', 'USER', 'test_user');
insert into bonus (id, count, user_id)
values (0, 0, 1);