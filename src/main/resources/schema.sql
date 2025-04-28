use
hhplus;

drop table if exists user;
drop table if exists user_cash;
drop table if exists user_cash_history;
drop table if exists product;
drop table if exists orders;
drop table if exists order_item;
drop table if exists coupon;
drop table if exists coupon_issue;
drop table if exists payment;
drop table if exists daily_product_sale;
drop table if exists best_selling_product;

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

create table user_cash
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '사용자별 캐시 id',
    user_id            VARCHAR(50)    NOT NULL UNIQUE COMMENT '사용자 id',
    balance            DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '잔액',
    version            INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '버전',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

create table user_cash_history
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '잔액 history id',
    user_id            BIGINT UNSIGNED NOT NULL COMMENT '사용자 id',
    balance            DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '잔액',
    type               VARCHAR(50)    NOT NULL COMMENT '잔액 사용 or 잔액 충전',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

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

CREATE TABLE orders
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '주문 id',
    user_id            INT UNSIGNED NOT NULL COMMENT '사용자 id',
    status             VARCHAR(50)    NOT NULL COMMENT '주문 상태',
    coupon_issue_id    BIGINT UNSIGNED NULL COMMENT '쿠폰 id',
    paid_date          DATETIME NULL COMMENT '결제 생성 시간',
    total_price        DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '총 주문금액',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 주문 항목
CREATE TABLE order_item
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '주문 항목 id',
    order_id           BIGINT UNSIGNED NOT NULL COMMENT '주문 id',
    product_id         BIGINT UNSIGNED NOT NULL COMMENT '상품 id',
    quantity           INT UNSIGNED NOT NULL COMMENT '상품 수',
    price              DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '상품 가격',
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
    discount_value     INT UNSIGNED NOT NULL COMMENT '할인 값(1000원 or 10%)',
    stock              INT UNSIGNED NOT NULL COMMENT '쿠폰 재고',
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

-- 결제 정보
CREATE TABLE payment
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '결제 id',
    order_id           BIGINT UNSIGNED NOT NULL COMMENT 'order id',
    amount             DECIMAL(18, 2) NOT NULL COMMENT '총 결제금액',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 일별 상품 집계
CREATE TABLE daily_product_sale
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '일별 상품 집계 id',
    base_date          DATE         NOT NULL COMMENT '기준일',
    product_id         BIGINT UNSIGNED NOT NULL COMMENT '상품 id',
    sales_count        INT UNSIGNED NOT NULL COMMENT '판매 수량',
    created_date       DATETIME     NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255) NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);

-- 특정 기간의 베스트 셀러 상품 정보
create table best_selling_product
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '베스트셀러 상품 id',
    start_date         DATE           NOT NULL COMMENT '조회 시작일',
    end_date           DATE           NOT NULL COMMENT '조회 종료일',
    product_id         BIGINT UNSIGNED NOT NULL COMMENT '상품 id',
    sales_count        INT UNSIGNED NOT NULL COMMENT '판매 수량',
    name               VARCHAR(255)   NOT NULL COMMENT '상품명',
    price              DECIMAL(18, 2) NOT NULL DEFAULT 0 COMMENT '가격',
    created_date       DATETIME       NOT NULL COMMENT '생성일',
    created_by         VARCHAR(255)   NOT NULL COMMENT '생성자',
    last_modified_date DATETIME NULL COMMENT '수정일',
    last_modified_by   VARCHAR(255) NULL COMMENT '수정자'
);


CREATE INDEX idx_user_id on user_cash_history (user_id);
CREATE INDEX idx_order_id on order_item (order_id);

ALTER TABLE coupon_issue
    ADD CONSTRAINT coupon_issue_unique UNIQUE (user_id, coupon_id);

CREATE INDEX idx_daily_sale_base_date_product
    ON daily_product_sale (base_date, product_id);

CREATE INDEX idx_best_selling_period
    ON best_selling_product (start_date, end_date);
