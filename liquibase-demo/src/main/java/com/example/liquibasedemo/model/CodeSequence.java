package com.example.liquibasedemo.model;

/**
 * WARNING: Each sequence defined in this enum **must exist** in the database.
 * - Before adding a new entry, ensure the corresponding sequence is created via Liquibase or SQL.
 * - Missing sequences will cause runtime errors during code generation.
 */
public enum CodeSequence {
    // Each sequence must be created in the database beforehand
    DEPARTMENT("department_code_seq", "DEPT"),
    SECTION("section_code_seq", "SEC");

    private final String sequenceName;
    private final String codePrefix;

    CodeSequence(String sequenceName, String codePrefix) {
        this.sequenceName = sequenceName;
        this.codePrefix = codePrefix;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public String getCodePrefix() {
        return codePrefix;
    }
}

