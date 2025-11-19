--liquibase formatted sql
--changeset sabryragab:v1.0.0_01_create_departments_table

CREATE TABLE departments (
     id BIGSERIAL PRIMARY KEY,
     code VARCHAR(50) NOT NULL UNIQUE,
     name VARCHAR(255) NOT NULL
);


--rollback DROP TABLE departments;

