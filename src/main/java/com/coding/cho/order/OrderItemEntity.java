package com.coding.cho.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.coding.cho.goods.GoodsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="order_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class OrderItemEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gno")
	private GoodsEntity goods;//
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ono")
	private OrderEntity order;
	
	private int orderPrice;
	
	private int count;
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp(6) null")
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@Column(columnDefinition = "timestamp(6) null")
	private LocalDateTime updatedTime;
}
