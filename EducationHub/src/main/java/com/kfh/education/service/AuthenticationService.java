package com.kfh.education.service;

import com.kfh.education.dto.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto login(String username, String password);
    void logout(String username);
}