package com.coding.cho.order.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class SaveCateDTO {
	
	private long gno;
	private String name;
	private int count;
	private int price;
	private String email;
}
