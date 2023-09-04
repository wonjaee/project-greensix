package com.coding.cho.order.dto;

import com.coding.cho.goods.dto.GoodsDetailDTO;
import com.coding.cho.order.CartItemEntity;

import lombok.Getter;

@Getter
public class CartItemDTO {
	
	private long no;
	
	private GoodsDetailDTO goods;
	
	private int count;

	public CartItemDTO(CartItemEntity e) {
		this.no = e.getNo();
		this.goods = new GoodsDetailDTO(e.getGoods());
		this.count = e.getCount();
	}
	

}
