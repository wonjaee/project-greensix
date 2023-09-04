package com.coding.cho.common.domain.dto.cscenter;

import java.time.LocalDateTime;

import com.coding.cho.common.domain.entity.FaqBoardEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FaqBoardListDTO {
	
	private long no;
	private String title;
	private String cotnent;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	
	@Builder
	public FaqBoardListDTO(FaqBoardEntity e) {
		this.no = e.getNo();
		this.title = e.getTitle();
	}
	
	

}
