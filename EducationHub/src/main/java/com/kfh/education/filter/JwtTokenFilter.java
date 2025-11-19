package com.kfh.education.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kfh.education.config.SecurityConfig;
import com.kfh.education.exception.SessionExpiredException;
import com.kfh.education.service.JwtCacheService;
import com.kfh.education.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final JwtCacheService jwtCacheService;
	private final UserDetailsService userDetailsService;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
			JwtCacheService jwtCacheService,
			UserDetailsService userDetailsService) {
		super();
		this.jwtTokenUtil = jwtTokenUtil;
		this.jwtCacheService = jwtCacheService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (isPublic(request.getRequestURI())) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = extractToken(request);
			if (token == null || !jwtTokenUtil.validateToken(token)) {
				throw new SessionExpiredException();
			}

			String username = jwtTokenUtil.extractUsername(token);

			// Validate that the user is not logged out
			if (jwtCacheService.get(username) == null) {
				throw new SessionExpiredException();
			}

			UserDetails userDetails = userDetailsService
				.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			auth
				.setDetails(new WebAuthenticationDetailsSource()
					.buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(auth);

			filterChain.doFilter(request, response);
		} catch (SessionExpiredException ex) {
			handleSessionExpired(response);
		}
	}

	private boolean isPublic(String path) {
		return Arrays
			.stream(SecurityConfig.PUBLIC_ENDPOINTS)
			.anyMatch(path::startsWith);
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return (header != null && header.startsWith("Bearer "))
				? header.substring(7)
				: null;
	}

	private void handleSessionExpired(HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		Map<String, List<String>> body = Map
			.of("errors",
					List.of("Your session has expired. Please log in again."));

		String json = new ObjectMapper().writeValueAsString(body);
		response.getWriter().write(json);
	}

}