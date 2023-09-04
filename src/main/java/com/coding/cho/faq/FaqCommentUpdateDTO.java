package com.coding.cho.faq;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqCommentUpdateDTO {

		
	private String content;
	public LocalDateTime updatedDate;

	@Builder
	public FaqCommentUpdateDTO(String editComment) {
		this.content=editComment;
	}
}
