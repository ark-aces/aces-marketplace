ALTER TABLE services ADD COLUMN label VARCHAR(255) NOT NULL DEFAULT '';
CREATE INDEX ON services (label);

ALTER TABLE services ADD COLUMN status VARCHAR(255) NOT NULL DEFAULT 'active';
CREATE INDEX ON services (status);
