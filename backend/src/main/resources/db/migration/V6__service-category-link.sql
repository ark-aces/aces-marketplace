CREATE TABLE service_category_links (
  pid BIGSERIAL PRIMARY KEY,
  service_pid BIGINT NOT NULL REFERENCES services (pid),
  service_category_pid BIGINT NOT NULL REFERENCES service_categories (pid)
);
CREATE INDEX ON service_category_links (service_pid);
CREATE INDEX ON service_category_links (service_category_pid);
