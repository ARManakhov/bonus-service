INSERT INTO user_t (id, password, role, username)
VALUES (0, '$2a$10$Z3UETC4teFUKQYJle6PaFOrh8nYNBZ6VmxZ8kKUFx7IFoZwnUlame', 'ADMIN', 'admin')
ON CONFLICT DO NOTHING;
