package com.coding.cho.goods.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.coding.cho.category.CategoryEntity;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.goods.GoodsImageEntity;

import lombok.Data;
@Data
public class GoodsDetailDTO {

	private long gno;
	private String name;
	private String content;
	private int price;
	private boolean hotItem ;
	private boolean onSale ;

	private CategoryEntity category;
	private CategoryEntity parent;
	
	private int discount;
	private LocalDate startDate;
	private LocalDate endDate;
	
	
	private long cNo;
	private String cName;

	private String[] url; //s3경로
	private String[] orgName; //s3경로
	private String[] newName; //s3경로
	private String[] bucketKey; //파일명
	private boolean[] def ; //true:def-img
	
	private List<GoodsImageDTO> gie ;
	
	public GoodsDetailDTO(GoodsEntity entity){
		this.category=entity.getCategory();
		this.parent=entity.getCategory().getParent();
		this.gno=entity.getNo(); 
		this.name=entity.getName();
		this.content=entity.getContent();
		this.onSale=entity.isOnSale();
		this.hotItem=entity.isHotItem();
		this.cNo=entity.getCategory().getNo();
		this.cName=entity.getCategory().getName();
		this.price=entity.getPrice();
		this.gie=entity.getGie().stream()
				.distinct()
				.map(GoodsImageDTO::new)
				.collect(Collectors.toList());
		
		if(onSale==true) {
			this.discount=entity.getSale().getDiscount();
			this.startDate=entity.getSale().getStartDate().toLocalDate();
			this.endDate=entity.getSale().getEndDate().toLocalDate().minusDays(1);
		}
	}
	
}
