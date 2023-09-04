package com.coding.cho.map;

import lombok.Getter;

@Getter
public class StoreDTO {

	private long no;
	
	private String name;

	private String engName;

	private String callNumber;
	
	private String faxNumber;
	
	private String address;
	
	private boolean status;
	
	private String storeCode;

	public StoreDTO(StoreEntity ent) {
		this.no = ent.getNo();
		this.name = ent.getName();
		this.engName = ent.getEngName();
		this.callNumber = ent.getCallNumber();
		this.faxNumber = ent.getFaxNumber();
		this.address = ent.getAddress();
		this.status = ent.isStatus();
		this.storeCode = ent.getStoreCode();
	}
	
	
	
}
