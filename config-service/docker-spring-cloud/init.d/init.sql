-- CREATE DATABASE IF NOT EXISTS mydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- User
-- DROP USER IF EXISTS 'user'@'%';
-- CREATE USER 'user'@'%' IDENTIFIED BY 'user';
-- GRANT ALL PRIVILEGES ON mydb.* TO 'user'@'%';
-- FLUSH PRIVILEGES;

create table orders (
    id int auto_increment primary key,
    user_id varchar(50) not null,
    product_id varchar(20) not null,
    order_id varchar(50) not null,
    qty int default 0,
    unit_price int default 0,
    total_price int default 0,
    created_at datetime default now()
);
