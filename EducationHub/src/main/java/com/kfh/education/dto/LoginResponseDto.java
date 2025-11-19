package com.kfh.education.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDto {
    private String username;
    private String token;

    @Override
    public String toString() {
        return "LoginResponseDto{" +
                "username='" + username + '\'' +
                '}';
    }
}
