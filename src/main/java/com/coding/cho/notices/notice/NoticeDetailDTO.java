package com.coding.cho.notices.notice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoticeDetailDTO {
	private long no;
	private String title;
	private String content;
	private LocalDate createdDate;
}
