package com.kfh.education.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfh.education.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUsernameIgnoreCase(String username);

}
