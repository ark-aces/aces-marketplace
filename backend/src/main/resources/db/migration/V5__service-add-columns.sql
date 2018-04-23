
ALTER TABLE services ADD COLUMN is_testnet BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE services ADD COLUMN flat_fee DECIMAL(40, 8);
ALTER TABLE services ADD COLUMN percent_fee DECIMAL(4, 2);
CREATE INDEX ON services (is_testnet);
CREATE INDEX ON services (flat_fee);
CREATE INDEX ON services (percent_fee);


CREATE TABLE service_capacities (
  pid BIGSERIAL PRIMARY KEY,
  service_id BIGINT NOT NULL REFERENCES services (pid),
  unit VARCHAR(255),
  value DECIMAL(40, 8)
);
CREATE INDEX ON service_capacities (service_id);
CREATE INDEX ON service_capacities (unit);
CREATE INDEX ON service_capacities (value);
