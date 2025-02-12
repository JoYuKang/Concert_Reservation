-- tb_member basic data
INSERT INTO tb_member (name, balance, version) VALUES
                                        ('Alice', 50000, 0),
                                        ('Bob', 30000, 0),
                                        ('Kang', 70000, 0),
                                        ('Kim', 70000, 0),
                                        ('Kong', 70000, 0),
                                        ('Hoo', 70000, 0),
                                        ('Ace', 70000, 0),
                                        ('Nana', 70000, 0);

-- tb_token basic data
INSERT INTO tb_token (token, status, created_at, expired_at) VALUES
                                                                (UUID(), 'ACTIVE', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE)),
                                                                (UUID(), 'ACTIVE', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));

-- tb_concert basic data
INSERT INTO tb_concert (title, concert_date, created_at, updated_at) VALUES
                                                                ('Rock Festival', '2025-02-01', NOW(), NOW()),
                                                                ('Winter Festival', '2025-03-11', NOW(), NOW()),
                                                                ('Winter Fishing Festival', '2025-03-15', NOW(), NOW()),
                                                                ('Jazz Night', '2025-03-15', NOW(), NOW());


-- tb_reservation basic data
INSERT INTO tb_reservation (member_id, concert_id, total_amount, status, created_at, updated_at) VALUES
    (1, 1, 20000, 'AWAITING_PAYMENT', NOW(), NOW()),
    (8, 2, 50000, 'AWAITING_PAYMENT', NOW(), NOW());
-- tb_seat basic data
INSERT INTO tb_seat (concert_id, position, amount, status, version) VALUES
                                                                (1, 1, 10000, 'AVAILABLE', 0),
                                                                (1, 2, 12000, 'AVAILABLE', 0),
                                                                (1, 3, 15000, 'AVAILABLE', 0),
                                                                (1, 4, 18000, 'AVAILABLE', 0),
                                                                (2, 1, 10000, 'AVAILABLE', 0),
                                                                (2, 2, 12000, 'AVAILABLE', 0),
                                                                (2, 3, 15000, 'AVAILABLE', 0),
                                                                (2, 4, 18000, 'AVAILABLE', 0);
INSERT INTO tb_seat (concert_id, position, amount, status, reservation_id, version) VALUES
                                                                (1, 14, 20000, 'SOLD_OUT', 1, 0),
                                                                (1, 15, 20000, 'SOLD_OUT', 1, 0);

-- tb_balance_history basic data
INSERT INTO tb_balance_history (member_id, amount, status, created_at) VALUES
                                                                           (1, 2000, 'USE', NOW()),
                                                                           (2, 2000, 'CHARGE', NOW());
