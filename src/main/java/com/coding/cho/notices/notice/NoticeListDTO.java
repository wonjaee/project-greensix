package com.coding.cho.notices.notice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoticeListDTO {
	
	private long no;
	private String title;
	private String content;
	private LocalDate createdDate;
	
	public NoticeListDTO(NoticeEntity noticeEntity) {
		no=noticeEntity.getNo();
		title=noticeEntity.getTitle();
		content=noticeEntity.getContent();
		createdDate=noticeEntity.getCreatedDate();
	}
}
