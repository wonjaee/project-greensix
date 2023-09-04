package com.coding.cho.common.domain.dto.cscenter;

import com.coding.cho.common.domain.entity.FaqDivision;
import com.coding.cho.common.domain.entity.FaqEntity;

import lombok.Getter;

@Getter
public class FaqListDTO {
	
	private long no;
	private FaqDivision division;
	private String question;
	private String answer;
	
	public FaqListDTO( FaqEntity e) {
		this.no = e.getNo();
		this.division = e.getDivision();
		this.question = e.getQuestion();
		this.answer = e.getAnswer();
	}
	
	

}
