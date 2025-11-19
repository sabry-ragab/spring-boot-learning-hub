
package com.example.oauth2demoapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // Login filter chain — handles OAuth2 login endpoints
    @Bean
    @Order(1)
    public SecurityFilterChain loginFilterChain(HttpSecurity http,
                                                OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) throws Exception {

        http
                .securityMatcher(
                        "/login/oauth2/code/google",  // Google callback endpoint
                        "/oauth2/authorization/google" // Start login flow
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/oauth2/authorization/google",
                                "/login/oauth2/code/google"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2LoginSuccessHandler)
                );

        return http.build();
    }

    // Resource filter chain — handles API endpoints
    @Bean
    @Order(2)
    public SecurityFilterChain resourceFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
