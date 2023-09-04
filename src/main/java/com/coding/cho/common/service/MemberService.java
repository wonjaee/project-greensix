package com.coding.cho.common.service;

import com.coding.cho.common.domain.dto.member.MemberSaveDTO;

public interface MemberService {

	void saveProcess(MemberSaveDTO encodePassword);

	boolean emailCheckProcess(String email);

}
