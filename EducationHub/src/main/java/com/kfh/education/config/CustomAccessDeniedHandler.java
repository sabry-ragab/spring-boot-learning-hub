package com.kfh.education.config;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {

		System.out.println("🔒 Access Denied:");
		System.out.println("→ URI: " + request.getRequestURI());
		System.out.println("→ Reason: " + accessDeniedException.getMessage());
		System.out
			.println("→ Auth: "
					+ SecurityContextHolder.getContext().getAuthentication());

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": \"Access denied\"}");
	}
}
