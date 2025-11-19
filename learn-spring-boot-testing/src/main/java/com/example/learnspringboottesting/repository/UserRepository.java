package com.example.learnspringboottesting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learnspringboottesting.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
