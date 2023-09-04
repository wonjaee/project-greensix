package com.coding.cho.goods.dto;

import com.coding.cho.goods.GoodsImageEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodsImageDTO {

	private long no;
	private String url; //s3경로
	private String orgName; 
	private String newName; 
	private String bucketKey; //파일명
	private boolean isDef ; //true:def-img
	
	public GoodsImageDTO(GoodsImageEntity entity) {
		this.no=entity.getNo();
		this.url=entity.getUrl();
		this.orgName=entity.getOrgName();
		this.newName=entity.getNewName();
		this.bucketKey=entity.getBucketKey();
		this.isDef=entity.isDef();
	}
}
