ALTER TABLE users ADD COLUMN email_address_verification_code VARCHAR(255);
CREATE INDEX ON users (email_address_verification_code);
