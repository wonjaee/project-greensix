package com.coding.cho.order.dto;

import com.coding.cho.order.OrderItemEntity;

import lombok.Getter;

@Getter
public class OrderItemDTO {
	
	private String name;
	private int price;
	private int count;
	public OrderItemDTO(OrderItemEntity ent) {
		super();
		this.name = ent.getGoods().getName();
		this.price = ent.getOrderPrice();
		this.count = ent.getCount();
	}

	
}
