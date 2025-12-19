-- Enable uuid-ossp extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create role table
CREATE TABLE IF NOT EXISTS role (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE
    );

-- Create app_user table
CREATE TABLE IF NOT EXISTS app_user (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role_id UUID REFERENCES role(id)
    );

-- Create member table
CREATE TABLE IF NOT EXISTS member (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
    );

-- Seed roles
INSERT INTO role (id, name) VALUES (uuid_generate_v4(), 'ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO role (id, name) VALUES (uuid_generate_v4(), 'ROLE_USER') ON CONFLICT (name) DO NOTHING;

-- Using {noop} for POC; switch to {bcrypt}<hash> for production.
INSERT INTO app_user (id, username, password_hash, role_id)
SELECT uuid_generate_v4(), 'balakrishna', 'balakrishna123', r.id FROM role r WHERE r.name = 'ROLE_ADMIN'
    ON CONFLICT (username) DO NOTHING;

INSERT INTO app_user (id, username, password_hash, role_id)
SELECT uuid_generate_v4(), 'balu', 'balu123', r.id FROM role r WHERE r.name = 'ROLE_USER'
    ON CONFLICT (username) DO NOTHING;

INSERT INTO app_user (id, username, password_hash, role_id)
SELECT uuid_generate_v4(), 'krishna', 'krishna', r.id FROM role r WHERE r.name = 'ROLE_USER'


