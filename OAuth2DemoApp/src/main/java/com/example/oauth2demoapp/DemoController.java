package com.example.oauth2demoapp;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/home")
    public String home() {
        return "Welcome, this is the home page!";
    }

    @GetMapping("/api/public/info")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/api/private/data")
    public String privateEndpoint() {
        return "This is a protected resource";
    }
}
