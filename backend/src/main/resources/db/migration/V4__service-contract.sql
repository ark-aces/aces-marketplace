
CREATE TABLE contracts (
  pid BIGSERIAL PRIMARY KEY,
  id VARCHAR(255),
  remote_contract_id VARCHAR(255),
  arguments_json TEXT,
  status VARCHAR(255),
  created_at TIMESTAMP,
  account_pid BIGINT REFERENCES accounts (pid) ON DELETE CASCADE,
  service_pid BIGINT REFERENCES services (pid) ON DELETE CASCADE
);
CREATE INDEX ON contracts (id);
CREATE INDEX ON contracts (status);
CREATE INDEX ON contracts (created_at);
CREATE INDEX ON contracts (account_pid);
CREATE INDEX ON contracts (service_pid);
