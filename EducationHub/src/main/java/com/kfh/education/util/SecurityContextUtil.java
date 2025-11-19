package com.kfh.education.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.kfh.education.model.Admin;

@Component
public class SecurityContextUtil {

	public Admin getLoggedInAdmin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null
				&& authentication.getPrincipal() instanceof Admin) {
			return (Admin) authentication.getPrincipal();
		}
		return null;
	}

}
