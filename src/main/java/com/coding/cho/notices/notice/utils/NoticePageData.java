package com.coding.cho.notices.notice.utils;



import lombok.Getter;

@Getter
public class NoticePageData {
	
	private int tot;//페이지총개수
	private int from; //출력되는 페이지 시작번호
	private int to; //출력되는 페이지 마직막 번호
	
	private boolean hasNext;
	private int page;
	
	
}
