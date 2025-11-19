package com.kfh.education.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kfh.education.model.Admin;
import com.kfh.education.repository.AdminRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private final AdminRepository adminRepository;

	public JwtUserDetailsService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Admin admin = adminRepository
			.findByUsernameIgnoreCase(username)
			.orElseThrow(
					() -> new UsernameNotFoundException("User not present"));
		return admin;
	}
}
