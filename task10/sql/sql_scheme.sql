CREATE DATABASE preproddb;

USE preproddb;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(15) NOT NULL UNIQUE,
    name VARCHAR(20),
    surname VARCHAR(20),
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    avatar VARCHAR(500)
);

CREATE TABLE subscriptions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE users_subscriptions (
    user_id INT NOT NULL,
    subscription_id INT NOT NULL,
    
    PRIMARY KEY(user_id, subscription_id),
    
    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT subscriptions_fk FOREIGN KEY (subscription_id) REFERENCES subscriptions (id) ON DELETE CASCADE
);

CREATE TABLE suppliers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    supplier_id INT,
    category VARCHAR(50),
    price DECIMAL(10, 2),
    description VARCHAR(500),
    image VARCHAR(100),

    CONSTRAINT suppliers_fk FOREIGN KEY (supplier_id) REFERENCES suppliers (id) ON DELETE CASCADE
);

CREATE TABLE cart (
    user_id INT,
    product_id INT,
    quantity INT,

    PRIMARY KEY(user_id, product_id),

    CONSTRAINT cart_users_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT cart_products_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

CREATE TABLE order_statuses (
    id INT PRIMARY KEY,
    status VARCHAR(15) NOT NULL
);

INSERT INTO order_statuses (id, status) VALUES
(1, 'ACCEPTED'), (2, 'CONFIRMED'), (3, 'IN_PROCESS'), (4, 'SENT'), (5, 'COMPLETED'), (6, 'CANCELLED');

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    status_id INT DEFAULT 1,
    time DATETIME,
    status_description VARCHAR(300),

    CONSTRAINT orders_users_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT order_statuses_fk FOREIGN KEY (status_id) REFERENCES order_statuses (id)
);

CREATE TABLE orders_products (
    order_id INT,
    product_id INT,
    quantity INT,
    current_price DECIMAL(10, 2),

    PRIMARY KEY(order_id, product_id),

    CONSTRAINT orders_products_order_fk FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT orders_products_product_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);