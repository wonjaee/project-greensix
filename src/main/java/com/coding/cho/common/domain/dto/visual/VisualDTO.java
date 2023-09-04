package com.coding.cho.common.domain.dto.visual;

import com.coding.cho.common.domain.entity.VisualEntity;

import lombok.Getter;

@Getter
public class VisualDTO {
	
	
	private long no;
	
	private String url;
	
	private int ordero;

	public VisualDTO(VisualEntity e) {
		super();
		this.no = e.getNo();
		this.url = e.getUrl();
		this.ordero = e.getOrderNo();
	}
	
	

}
