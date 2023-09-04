package com.coding.cho.event;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EventImageDTO {

	private long no;
	private String url; //s3경로
	private String orgName; 
	private String newName; 
	private String bucketKey; //파일명
	private boolean isDef ; //true:def-img
	
	public EventImageDTO(EventImageEntity entity) {
		this.no=entity.getNo();
		this.url=entity.getUrl();
		this.orgName=entity.getOrgName();
		this.newName=entity.getNewName();
		this.bucketKey=entity.getBucketKey();
		this.isDef=entity.isDef();
	}
}
