--liquibase formatted sql
--changeset sabryragab:v1.0.1_01_insert_more_departments

INSERT INTO departments (code, name) VALUES ('DEP-1', 'DEPARTMENT ONE');
INSERT INTO departments (code, name) VALUES ('DEP-2', 'DEPARTMENT TWO');

--rollback DELETE FROM departments WHERE code IN ('DEP-1', 'DEP-2');
