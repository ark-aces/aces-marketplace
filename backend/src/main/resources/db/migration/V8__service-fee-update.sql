ALTER TABLE services ADD COLUMN flat_fee_unit VARCHAR(32);
CREATE INDEX ON services (flat_fee_unit);
