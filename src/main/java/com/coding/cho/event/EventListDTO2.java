package com.coding.cho.event;



import lombok.Data;

@Data
public class EventListDTO2 {

	private String name;
	private String url;
	private String content;
	private long no;
	
	public EventListDTO2(EventEntity entity){
		this.name=entity.getName();
		this.content=entity.getContent();
		this.url=entity.getGie().stream()
				.filter(ie -> !ie.isDef())//isDef가 true인 GoodsImageEntity 객체만 추출
                .findFirst() //그중 첫번째 데이터만
                .map(EventImageEntity::getUrl) // 객체에서 url필드만 리턴
                .orElseThrow();//예외처리
		this.no=entity.getNo();
		
	}
	
}
	

