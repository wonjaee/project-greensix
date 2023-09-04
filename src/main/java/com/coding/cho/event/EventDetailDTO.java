package com.coding.cho.event;

import java.util.List;
import java.util.stream.Collectors;



import lombok.Data;
@Data
public class EventDetailDTO {

	private long gno;
	private String name;
	private String content;
	
	

	private String[] url; //s3경로
	private String[] orgName; //s3경로
	private String[] newName; //s3경로
	private String[] bucketKey; //파일명
	private boolean[] def ; //true:def-img
	
	private List<EventImageDTO> gie ;
	
	public EventDetailDTO(EventEntity entity){
		this.gno=entity.getNo(); 
		this.name=entity.getName();
		this.content=entity.getContent();
		
		
		this.gie=entity.getGie()
				.stream().map(en->new EventImageDTO(en))
				.collect(Collectors.toList());
		
	}
	
}
