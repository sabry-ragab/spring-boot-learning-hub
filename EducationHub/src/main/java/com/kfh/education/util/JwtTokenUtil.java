package com.kfh.education.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

	private final String secretKey;
	private final long expirationMs;

	public JwtTokenUtil(@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.expiration}") long expirationTime) {
		this.secretKey = secretKey;
		this.expirationMs = expirationTime;
	}

	public String generateToken(UserDetails userDetails) {
		return Jwts
			.builder()
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expirationMs))
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()),
					SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts
				.parserBuilder()
				.setSigningKey(secretKey.getBytes())
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	public String extractUsername(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
