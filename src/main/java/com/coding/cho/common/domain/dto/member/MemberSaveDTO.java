package com.coding.cho.common.domain.dto.member;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.coding.cho.common.domain.entity.MemberEntity;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class MemberSaveDTO {
	
	private String email;
	private String pass;
	private String name;
	
	public MemberSaveDTO encodePassword( PasswordEncoder pe ) {
		pass=pe.encode(pass);
		return this;
	}
	
	//저장시에는 Entity로 저장
	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.email(email)
				.pass(pass)
				.name(name)
				.build();
	}

}
