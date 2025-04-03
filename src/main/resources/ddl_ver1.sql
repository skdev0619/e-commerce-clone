use hhplus;

drop table if exists user;
drop table if exists user_cash;
drop table if exists cash_history;
drop table if exists product;
drop table if exists coupon;
drop table if exists coupon_issue;
drop table if exists orders;
drop table if exists order_item;
drop table if exists payment;
drop table if exists daily_product_sales;
drop table if exists top_selling_products;

-- 사용자
create table user
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 id',
    login_id           VARCHAR(50)  NOT NULL UNIQUE COMMENT '사용자 정의 아이디',
    password           VARCHAR(255) NOT NULL COMMENT '패스워드',
    phone_number       VARCHAR(50)  NOT NULL COMMENT '핸드폰번호',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 사용자 잔액
create table user_cash
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '사용자별 캐시 id',
    user_id            VARCHAR(50)    NOT NULL COMMENT '사용자 id',
    balance            DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '잔액',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 잔액 HISTORY
create table cash_history
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '잔액 history id',
    user_id            BIGINT UNSIGNED NOT NULL COMMENT '사용자 id',
    balance            DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '잔액',
    transaction_type   VARCHAR(50)    NOT NULL COMMENT '잔액 사용 or 잔액 충전',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 상품
create table product
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '상품 id',
    name               VARCHAR(255)   NOT NULL COMMENT '상품명',
    price              DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '가격',
    stock              INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '상품 재고',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 쿠폰
CREATE TABLE coupon
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '쿠폰 id',
    name               VARCHAR(255)   NOT NULL COMMENT '쿠폰명',
    discount_type      VARCHAR(50)    NOT NULL COMMENT '할인타입(정액, 정률)',
    discount_value     DECIMAL(10, 2) NOT NULL COMMENT '할인 값(1000원 or 10%)',
    max_discount       DECIMAL(10, 2) DEFAULT NULL COMMENT '최대 할인 금액',
    min_order_amount   DECIMAL(10, 2) DEFAULT NULL COMMENT '최소 주문 금액 제한',
    stock              INT UNSIGNED NOT NULL COMMENT '쿠폰 재고',
    expiration_date    DATETIME       NOT NULL COMMENT '쿠폰 만료일',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 사용자별 쿠폰
CREATE TABLE coupon_issue
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '사용자별 쿠폰 id',
    user_id            INT UNSIGNED NOT NULL COMMENT '사용자 id',
    coupon_id          INT UNSIGNED NOT NULL COMMENT '쿠폰 id',
    status             VARCHAR(50)  NOT NULL COMMENT '쿠폰 상태(사용가능, 사용함, 만료)',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 주문
CREATE TABLE orders
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '주문 id',
    user_id          INT UNSIGNED NOT NULL COMMENT '사용자 id',
    status             VARCHAR(50)  NOT NULL COMMENT '주문 상태',
    coupon_id          BIGINT UNSIGNED NOT NULL COMMENT '쿠폰 id',
    order_date_time    DATETIME     NOT NULL COMMENT '주문 생성일',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 주문 항목
CREATE TABLE order_item
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '주문 항목 id',
    order_id           BIGINT UNSIGNED NOT NULL COMMENT '주문 id',
    product_id         VARCHAR(50)  NOT NULL COMMENT '상품 id',
    quantity           DATETIME     NOT NULL COMMENT '상품 수',
    price              DATETIME     NOT NULL COMMENT '상품 가격',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 주문별 결재 내역
CREATE TABLE payment
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '결제 id',
    member_id          BIGINT UNSIGNED NOT NULL COMMENT 'member id',
    order_id           BIGINT UNSIGNED NOT NULL COMMENT 'order id',
    amount             DECIMAL(18, 2) NOT NULL COMMENT '총 결제금액',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 일별 상품 집계
CREATE TABLE daily_product_sales
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '일별 상품 집계 id',
    base_date          VARCHAR(8)   NOT NULL COMMENT '기준일',
    product_id         BIGINT UNSIGNED NOT NULL COMMENT '상품 id',
    sales_count        INT UNSIGNED NOT NULL COMMENT '판매 수량',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 최근 3일간 많이 판매된 상품 정보
CREATE TABLE top_selling_products
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '일별 상품 집계 id',
    base_date_from     VARCHAR(8)   NOT NULL COMMENT '기준일 from',
    base_date_to       VARCHAR(8)   NOT NULL COMMENT '기준일 to',
    product_id         BIGINT UNSIGNED NOT NULL COMMENT '상품 id',
    sales_count        INT UNSIGNED NOT NULL COMMENT '판매 수량',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);