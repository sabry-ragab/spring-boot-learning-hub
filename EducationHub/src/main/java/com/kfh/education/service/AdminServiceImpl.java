package com.kfh.education.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kfh.education.dto.AdminDto;
import com.kfh.education.exception.UsernameAlreadyExistException;
import com.kfh.education.mapper.AdminMapper;
import com.kfh.education.model.Admin;
import com.kfh.education.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	private final AdminRepository adminRepository;
	private final AdminMapper adminMapper;

	public AdminServiceImpl(AdminRepository adminRepository,
			AdminMapper adminMapper) {
		this.adminRepository = adminRepository;
		this.adminMapper = adminMapper;
	}

	@Override
	public AdminDto create(AdminDto adminDto) {
		Admin adminWithSameUsername = adminRepository
			.findByUsernameIgnoreCase(adminDto.getUsername())
			.orElse(null);
		if (adminWithSameUsername != null) {
			throw new UsernameAlreadyExistException();
		}

		Admin admin = adminMapper.toEntity(adminDto);
		Admin createdAdminDto = adminRepository.save(admin);
		return adminMapper.toDto(createdAdminDto);
	}

}