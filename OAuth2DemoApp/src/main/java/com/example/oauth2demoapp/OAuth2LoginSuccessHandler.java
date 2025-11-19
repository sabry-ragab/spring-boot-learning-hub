package com.example.oauth2demoapp;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String username = oAuth2User.getAttribute("email"); // or "sub" for Google ID
            String token = jwtUtil.generateToken(username);

            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // set to true in prod (HTTPS)
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // 1 hour

            response.addCookie(jwtCookie);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Login failed");
        }
    }
}
