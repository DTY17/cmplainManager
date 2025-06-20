CREATE DATABASE complaint_manager;
USE complaint_manager;

CREATE TABLE users (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    phone_number varchar(20),
    nic varchar(20) UNIQUE,
    role varchar(50) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE response (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) NOT NULL,
    date varchar(100) NOT NULL,
    time varchar(100) NOT NULL,
    response varchar(500) NOT NULL,
    state varchar(20)
);

CREATE TABLE reply (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    response_id int NOT NULL,
    reply_text text NOT NULL,
    FOREIGN KEY (response_id) REFERENCES response(id)
);