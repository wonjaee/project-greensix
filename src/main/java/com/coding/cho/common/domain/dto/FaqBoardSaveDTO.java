package com.coding.cho.common.domain.dto;

import com.coding.cho.common.domain.entity.FaqBoardEntity;
import com.coding.cho.common.domain.entity.MemberEntity;

import lombok.Setter;

@Setter
public class FaqBoardSaveDTO {

	
	private String title;
	private String content;
	
	public FaqBoardEntity toEntity(MemberEntity memberEntity) {
		return FaqBoardEntity.builder()
				.title(title).content(content).creator(memberEntity)
				.build();
		
	}
}
