package com.coding.cho.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.coding.cho.order.CartEntity;

import lombok.Getter;

@Getter
public class CartDTO {
	
	private long no;
	
	private List<CartItemDTO> cartItems;


	public CartDTO(CartEntity e) {
		this.no = e.getNo();
		this.cartItems = e.getCartItems().stream()
				.map(CartItemDTO::new)
				.collect(Collectors.toList());
	}
	
	
	

}
