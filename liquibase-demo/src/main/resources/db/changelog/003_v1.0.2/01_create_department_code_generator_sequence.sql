--liquibase formatted sql
--changeset sabryragab:v1.0.2_01_create_department_code_generator_sequence

CREATE SEQUENCE department_code_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    CACHE 20;

--rollback DROP SEQUENCE IF EXISTS department_code_seq;
