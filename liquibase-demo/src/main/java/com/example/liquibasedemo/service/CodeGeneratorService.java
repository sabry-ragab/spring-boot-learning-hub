package com.example.liquibasedemo.service;

import com.example.liquibasedemo.model.CodeSequence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

@Service
public class CodeGeneratorService {
    @PersistenceContext
    private EntityManager entityManager;

    public String generateNextCode(CodeSequence type) {
        try {
            String sql = "SELECT nextval('" + type.getSequenceName() + "')";
            long nextVal = ((Number) entityManager
                    .createNativeQuery(sql)
                    .getSingleResult()).longValue();

            return type.getCodePrefix() + "-" + nextVal;
        } catch (PersistenceException e) {
            throw new RuntimeException("Failed to generate code for type: " + type.name(), e);
        }
    }

}
