package com.kfh.education.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kfh.education.dto.AdminDto;
import com.kfh.education.model.Admin;

@Component
public class AdminMapper extends AbstractMapper<AdminDto, Admin> {

	private final PasswordEncoder passwordEncoder;

	public AdminMapper(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Class<AdminDto> getDtoClass() {
		return AdminDto.class;
	}

	@Override
	public Class<Admin> getEntityClass() {
		return Admin.class;
	}

	@Override
	protected void afterMapDtoToEntity(Admin entity, AdminDto dto) {
		if (dto.getPassword() != null) {
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
	}
	
	@Override
	protected void afterMapEntityToDto(Admin entity, AdminDto dto) {
		dto.setPassword(null); // Do not expose password in DTO
    }
	
}