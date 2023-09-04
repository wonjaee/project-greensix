package com.coding.cho.map;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.coding.cho.common.domain.entity.MemberEntity;
import com.coding.cho.order.CartEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stores")
@Getter
@Entity
public class StoreEntity {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String engName;
	
	@Column(nullable = false)
	private String callNumber;
	
	@Column(nullable = false)
	private String faxNumber;
	
	@Column(nullable = false)
	private String address;
	
	private boolean status;
	
	private String storeCode;
	
	@OneToOne
	@JoinColumn(name="mno")
	private MemberEntity member;
	
	
	
	public StoreEntity changeStatus(boolean tf) {
		this.status=tf;
		return this;
	}
}



