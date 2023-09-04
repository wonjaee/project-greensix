package com.coding.cho.common.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.coding.cho.common.domain.dto.member.MemberSaveDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Aspect
@Component
public class AopConfig {

	private final PasswordEncoder passwordEncoder;
	
	//포인트 컷 표현식
	@Before("@annotation(org.springframework.web.bind.annotation.PostMapping) && "
			+ "execution(* com.coding.cho.controller.*.signup(..)) && args(dto)")
    public void encodePasswordBeforeSignup(MemberSaveDTO dto) {
        dto.encodePassword(passwordEncoder);
    }
}
