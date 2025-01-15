-- tb_member basic data
INSERT INTO tb_member (name, balance) VALUES
                                        ('Alice', 50000),
                                        ('Bob', 30000);

-- tb_token basic data
INSERT INTO tb_token (token, status, created_at, expired_at) VALUES
                                                                (UUID(), 'ACTIVE', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE)),
                                                                (UUID(), 'ACTIVE', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));

-- tb_concert basic data
INSERT INTO tb_concert (title, concert_date) VALUES
                                                ('Rock Festival', '2025-02-01'),
                                                ('Jazz Night', '2025-03-15');

-- tb_reservation basic data
INSERT INTO tb_reservation (member_id, concert_id, total_amount, status) VALUES
    (1, 1, 20000, 'AWAITING_PAYMENT');

-- tb_seat basic data
INSERT INTO tb_seat (concert_id, position, amount, status) VALUES
                                                                (1, 1, 100, 'AVAILABLE'),
                                                                (1, 2, 120, 'AVAILABLE'),
                                                                (1, 3, 150, 'AVAILABLE'),
                                                                (1, 4, 180, 'AVAILABLE'),
                                                                (2, 1, 100, 'AVAILABLE'),
                                                                (2, 2, 120, 'AVAILABLE'),
                                                                (2, 3, 150, 'AVAILABLE'),
                                                                (2, 4, 180, 'AVAILABLE');
INSERT INTO tb_seat (concert_id, position, amount, status, reservation_id) VALUES
                                                                (1, 5, 200, 'SOLD_OUT', 1),
                                                                (2, 5, 200, 'SOLD_OUT', 1);

-- tb_balance_history basic data
INSERT INTO tb_balance_history (member_id, amount, status, created_at) VALUES
                                                                           (1, 2000, 'USE', NOW()),
                                                                           (2, 2000, 'CHARGE', NOW());