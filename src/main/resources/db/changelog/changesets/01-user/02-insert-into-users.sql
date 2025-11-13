-- liquibase formatted sql
-- changeset kiran.khanal:users-insert-v1 context:dev
-- preconditions onFail:CONTINUE onError: HALT
INSERT INTO users (username, full_name, email, password, is_deleted, created_at, updated_at)
VALUES
    ('kiran01', 'Kiran Khanal', 'kiran@example.com', 'hashed_password', FALSE, NOW(), NOW()),
    ('sita02', 'Sita Sharma', 'sita@example.com', 'hashed_password', FALSE, NOW(), NOW()),
    ('ram03', 'Ram Thapa', 'ram@example.com', 'hashed_password', FALSE, NOW(), NOW()),
    ('anita04', 'Anita Gurung', 'anita@example.com', 'hashed_password', FALSE, NOW(), NOW()),
    ('bishal05', 'Bishal Rai', 'bishal@example.com', 'hashed_password', FALSE, NOW(), NOW());
