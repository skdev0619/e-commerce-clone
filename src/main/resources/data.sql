use
hhplus;

-- user
INSERT INTO user (login_id, password, phone_number, created_date, created_by)
VALUES ('user1', '1q2w3e4r', '010-1234-5678', NOW(), 'admin'),
       ('user2', '1q2w3e4r', '010-1234-5678', NOW(), 'admin'),
       ('user3', '1q2w3e4r', '010-1234-5678', NOW(), 'admin');

-- user_cash
INSERT INTO user_cash (user_id, balance, version, created_date, created_by)
VALUES (1, 100000.00, 0, NOW(), 'admin'),
       (2, 200000.00, 0, NOW(), 'admin'),
       (3, 150000.00, 0, NOW(), 'admin');

-- user_cash_history
INSERT INTO user_cash_history (user_id, balance, type, created_date, created_by)
VALUES (1, 10000.00, 'CHARGE', NOW(), 'admin'),
       (1, 5000.00, 'USE', NOW(), 'admin'),
       (2, 20000.00, 'CHARGE', NOW(), 'admin'),
       (3, 15000.00, 'CHARGE', NOW(), 'admin');

-- product
INSERT INTO product (name, price, stock, created_date, created_by)
VALUES ('검정볼캡', 5000.00, 100, NOW(), 'admin'),
       ('회색볼캡', 10000.00, 90, NOW(), 'admin'),
       ('베이지볼캡', 15000.00, 80, NOW(), 'admin'),
       ('파랑볼캡', 15000.00, 70, NOW(), 'admin'),
       ('빨강볼캡', 15000.00, 60, NOW(), 'admin');

-- orders
INSERT INTO orders (user_id, status, coupon_issue_id, paid_date, total_price, created_date, created_by)
VALUES (1, 'PAID', NULL, NOW(), 20000.00, NOW(), 'admin'),
       (2, 'PAID', NULL, NOW(), 30000.00, NOW(), 'admin'),
       (3, 'PAID', NULL, NOW(), 5000.00, NOW(), 'admin');

-- order_item
INSERT INTO order_item (order_id, product_id, quantity, price, created_date, created_by)
VALUES (1, 1, 2, 5000.00, NOW(), 'admin'),
       (1, 2, 1, 10000.00, NOW(), 'admin'),
       (2, 3, 2, 15000.00, NOW(), 'admin'),
       (3, 1, 1, 5000.00, NOW(), 'admin');

-- coupon
INSERT INTO coupon (name, discount_type, discount_value, stock, created_date, created_by)
VALUES ('1000원 할인', 'FIXED_AMOUNT', 1000, 100, NOW(), 'admin'),
       ('10% 할인', 'PERCENTAGE', 10, 50, NOW(), 'admin');

-- coupon_issue
INSERT INTO coupon_issue (user_id, coupon_id, status, created_date, created_by)
VALUES (2, 1, 'ACTIVE', NOW(), 'admin'),
       (2, 2, 'USED', NOW(), 'admin'),
       (3, 1, 'ACTIVE', NOW(), 'admin'),
       (3, 2, 'EXPIRED', NOW(), 'admin');

-- payment
INSERT INTO payment (order_id, amount, created_date, created_by)
VALUES (1, 20000.00, NOW(), 'admin'),
       (2, 30000.00, NOW(), 'admin'),
       (3, 5000.00, NOW(), 'admin');

-- daily_product_sale
INSERT INTO daily_product_sale (base_date, product_id, sales_count, created_date, created_by)
VALUES (CURDATE(), 1, 50, NOW(), 'admin'),
       (CURDATE(), 2, 30, NOW(), 'admin'),
       (CURDATE(), 3, 20, NOW(), 'admin');

-- best_selling_product
INSERT INTO best_selling_product (start_date, end_date, product_id, sales_count, name, price, created_date, created_by)
VALUES ('2025-04-24', '2025-04-26', 1, 100, '검정볼캡', 10000.00, NOW(), 'admin'),
       ('2025-04-24', '2025-04-26', 2, 90, '회색볼캡', 15000.00, NOW(), 'admin'),
       ('2025-04-24', '2025-04-26', 3, 80, '베이지볼캡', 15000.00, NOW(), 'admin'),
       ('2025-04-24', '2025-04-26', 4, 70, '파랑볼캡', 15000.00, NOW(), 'admin'),
       ('2025-04-24', '2025-04-26', 5, 60, '빨강볼캡', 15000.00, NOW(), 'admin');

