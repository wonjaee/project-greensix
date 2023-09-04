package com.coding.cho.menu;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.goods.GoodsImageEntity;
import com.coding.cho.goods.dto.SaleListDTO;

import lombok.Builder;
import lombok.Data;


@Data
public class MenuListDTO {
	private String name;
	private int price;
	private String url;
	private String content;
	private long no;
	
	private boolean onSale;
	private SaleListDTO sale;
	
	
	public MenuListDTO(GoodsEntity entity){
		this.name=entity.getName();
		this.price=entity.getPrice();
		this.content=entity.getContent();
		this.url=entity.getGie().stream()
				.filter(ie -> ie.isDef())//isDef가 true인 GoodsImageEntity 객체만 추출
                .findFirst() //그중 첫번째 데이터만
                .map(GoodsImageEntity::getUrl) // 객체에서 url필드만 리턴
                .orElseThrow();//예외처리
		this.no=entity.getNo();
		if(entity.isOnSale()==true) {
			if(entity.getSale().getEndDate()!=null) {
				LocalDateTime endD= entity.getSale().getEndDate();
				LocalDateTime startD= entity.getSale().getStartDate();
				if(startD.isBefore(LocalDateTime.now())&&endD.isAfter(LocalDateTime.now())) {
					this.onSale=entity.isOnSale();
					this.sale=new SaleListDTO(entity.getSale());
				}
			}else if(entity.getSale().getEndDate()==null) {
				this.onSale=entity.isOnSale();
				this.sale=new SaleListDTO(entity.getSale());
			}
			
		}
		
		
	}
	
}
