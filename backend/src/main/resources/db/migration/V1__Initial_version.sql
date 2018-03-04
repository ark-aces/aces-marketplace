CREATE TABLE accounts (
  pid BIGSERIAL PRIMARY KEY,
  id VARCHAR(255),
  contact_email_address VARCHAR(255),
  email_address_verification_code VARCHAR(255),
  is_contact_email_address_verified BOOLEAN NOT NULL,
  ark_wallet_address VARCHAR(255),
  created_at TIMESTAMP NOT NULL
);
CREATE INDEX ON accounts (id);
CREATE INDEX ON accounts (contact_email_address);
CREATE INDEX ON accounts (is_contact_email_address_verified);
CREATE INDEX ON accounts (created_at);


CREATE TABLE service_categories (
  pid BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  position INT4
);
CREATE INDEX ON service_categories (name);
CREATE INDEX ON service_categories (position);


CREATE TABLE users (
  pid BIGSERIAL PRIMARY KEY,
  id VARCHAR(255),
  name VARCHAR(255),
  email_address VARCHAR(255),
  is_email_address_verified BOOLEAN,
  password_hash VARCHAR(255),
  created_at TIMESTAMP,
  account_pid BIGINT REFERENCES accounts (pid) ON DELETE CASCADE
);
CREATE INDEX ON users (id);
CREATE INDEX ON users (name);
CREATE INDEX ON users (email_address);
CREATE INDEX ON users (is_email_address_verified);
CREATE INDEX ON users (created_at);
CREATE INDEX ON users (account_pid);


CREATE TABLE registrations (
  pid BIGSERIAL PRIMARY KEY,
  id VARCHAR(255),
  created_at TIMESTAMP,
  account_pid BIGINT REFERENCES accounts (pid) ON DELETE CASCADE
);
CREATE INDEX ON registrations (id);
CREATE INDEX ON registrations (created_at);
CREATE INDEX ON registrations (account_pid);


CREATE TABLE services (
  pid BIGSERIAL PRIMARY KEY,
  id VARCHAR(255),
  url VARCHAR(255),
  name VARCHAR(255),
  version VARCHAR(20),
  description VARCHAR(255),
  website_url VARCHAR(255),
  created_at TIMESTAMP,
  account_pid BIGINT REFERENCES accounts (pid) ON DELETE CASCADE
);
CREATE INDEX ON services (id);
CREATE INDEX ON services (created_at);
CREATE INDEX ON services (account_pid);
