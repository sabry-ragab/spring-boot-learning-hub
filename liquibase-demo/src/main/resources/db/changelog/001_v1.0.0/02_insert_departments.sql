--liquibase formatted sql
--changeset sabryragab:v1.0.0_02_insert_departments

INSERT INTO departments (code, name) VALUES ('HR', 'Human Resources');
INSERT INTO departments (code, name) VALUES ('IT', 'Information Technology');

--rollback DELETE FROM departments WHERE code IN ('HR', 'IT');

--changeset sabryragab:v1.0.0_02_insert_prod_departments context:prod
INSERT INTO departments (code, name) VALUES ('FINANCE', 'Finance');
INSERT INTO departments (code, name) VALUES ('MARKETING', 'Marketing');

--rollback DELETE FROM departments WHERE code IN ('FINANCE', 'MARKETING');

--changeset sabryragab:v1.0.0_02_insert_dev_departments context:dev

INSERT INTO departments (code, name) VALUES ('SALES', 'Sales');
INSERT INTO departments (code, name) VALUES ('BUSINESS', 'Business');

--rollback DELETE FROM departments WHERE code IN ('SALES', 'BUSINESS');

