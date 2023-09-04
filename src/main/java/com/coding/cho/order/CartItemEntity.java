package com.coding.cho.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coding.cho.goods.GoodsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "cart_item")
@Entity
public class CartItemEntity {

	
	@Id 
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long no;
	
	@ManyToOne
	@JoinColumn(name="cno")
	private CartEntity cart;
	
	@JoinColumn(name = "gno", nullable = true)
	@ManyToOne
	private GoodsEntity goods;
	
	
	@Column(nullable = false)
	private int count;             //상품 개수
	
	
	public CartItemEntity addCount(int count) {
		this.count+=count;
		return this;
		
	}
	public CartItemEntity deleteCount(int count) {
		this.count-=count;
		return this;
		
	}
	
}
