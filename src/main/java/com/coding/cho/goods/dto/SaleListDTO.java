package com.coding.cho.goods.dto;

import java.time.LocalDateTime;

import com.coding.cho.goods.SaleEntity;

import lombok.Getter;

@Getter
public class SaleListDTO {
	
	private long no;
	private int discount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public SaleListDTO(SaleEntity entity) {
		this.no=entity.getNo();
		this.discount = entity.getDiscount();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
	}

}
