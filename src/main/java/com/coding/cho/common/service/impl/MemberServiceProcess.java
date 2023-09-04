package com.coding.cho.common.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.cho.common.domain.dto.member.MemberSaveDTO;
import com.coding.cho.common.domain.entity.MemberEntityRepository;
import com.coding.cho.common.domain.entity.MyRole;
import com.coding.cho.common.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceProcess implements MemberService{
	//DAO
	private final MemberEntityRepository repository;
	private final PasswordEncoder pe;
	
	@Override
	public void saveProcess(MemberSaveDTO encodedDto) {
		//일반회원가입 처리 
		repository.save( encodedDto.encodePassword(pe)//pass 암호화된 DTO
				.toEntity() //dto->entity 객체로
				.addRole(MyRole.USER) //USER ROLE부여
				);
		
	}

	@Override
	public boolean emailCheckProcess(String email) {
		// true: 존재시(사용불가)
		// false: 사용가능
		//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.namespace-reference
		return repository.existsByEmail(email);
	}
	

}
