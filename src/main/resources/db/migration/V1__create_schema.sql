-- Create users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    user_name VARCHAR(255),
    username VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    website VARCHAR(255),
    address_street VARCHAR(255),
    address_suite VARCHAR(255),
    address_city VARCHAR(255),
    address_zipcode VARCHAR(255),
    geo_lat VARCHAR(255),
    geo_lng VARCHAR(255),
    company_name VARCHAR(255),
    company_catch_phrase VARCHAR(255),
    company_bs VARCHAR(255)
);

-- Create posts table
CREATE TABLE posts (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    body TEXT,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create albums table
CREATE TABLE albums (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create todos table
CREATE TABLE todos (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    completed BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 