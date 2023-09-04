package com.coding.cho.notices.notice;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {
	private long no;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	
	
	
	
	
}
