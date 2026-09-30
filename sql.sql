CREATE DATABASE localguide;

USE localguide;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,

    first_name VARCHAR(50) NOT NULL,

    middle_name VARCHAR(50),

    last_name VARCHAR(50) NOT NULL,

    email VARCHAR(120) NOT NULL UNIQUE,

    phone VARCHAR(10) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    password_salt VARCHAR(255) NOT NULL,

    role ENUM('CUSTOMER', 'GUIDE', 'ADMIN') NOT NULL,

    account_status ENUM('ACTIVE', 'BLOCKED', 'PENDING')
        DEFAULT 'ACTIVE',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM users;
