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
    CONSTRAINT subscrioptions_fk FOREIGN KEY (subscription_id) REFERENCES subscriptions (id) ON DELETE CASCADE
);