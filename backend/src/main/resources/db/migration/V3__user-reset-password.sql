
CREATE TABLE user_reset_password_requests (
  pid BIGSERIAL PRIMARY KEY,
  code VARCHAR(255),
  is_used BOOLEAN,
  created_at TIMESTAMP,
  user_pid BIGINT REFERENCES users (pid) ON DELETE CASCADE
);
CREATE INDEX ON user_reset_password_requests (code);
CREATE INDEX ON user_reset_password_requests (user_pid);
