package com.coding.cho.goods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.coding.cho.goods.dto.GoodsSaveDTO;
import com.coding.cho.goods.dto.SaleSaveDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name="Sale")
@Entity
public class SaleEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	private int discount;
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@OneToOne	
	@JoinColumn(name="gno")
	private GoodsEntity goods;

	//goods-gno 편의메서드
	public SaleEntity goods(GoodsEntity goods) {
		this.goods=goods;
		return this;
	}
	public SaleEntity updateSale(SaleSaveDTO dto) {
		this.discount=dto.getDiscount();
		this.startDate=dto.getStartDate();
		this.endDate=dto.getEndDate();
		return this;
	}


	
	
}
