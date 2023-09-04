package com.coding.cho.event;

import java.util.ArrayList;
import java.util.List;

import com.coding.cho.common.domain.dto.S3UploadDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EventSaveDTO {

	private long gno;
	private String name;
	private String content;

	private String[] url; //s3경로
	private String[] orgName; //s3경로
	private String[] newName; //s3경로
	private String[] bucketKey; //파일명
	private boolean[] def ; //true:def-img
	
	private List<EventImageEntity> gie ;
	
	public List<S3UploadDTO> files(){
		List<S3UploadDTO> list=new ArrayList<>();
		for(int i=0; i<bucketKey.length ;i++) {
			if(bucketKey[i]!=null && bucketKey[i]!="") {
				list.add(S3UploadDTO.builder()
						.tempKey(bucketKey[i])
						.orgName(orgName[i])
						.newName(newName[i])
						.def(def[i])
						.build());
			}
		}
		return list;
	}
}
