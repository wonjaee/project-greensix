package com.coding.cho.event;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class EventUpdateDTO {

	private long gno;
	private String name;
	private String content;
	

	private String[] url; //s3경로
	private String[] orgName; //s3경로
	private String[] newName; //s3경로
	private String[] bucketKey; //파일명
	private boolean[] def ; //true:def-img
	
	private List<EventImageDTO> gie ;
}
