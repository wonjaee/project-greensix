package com.coding.cho.goods.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.coding.cho.goods.SaleEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaleSaveDTO {
	
	private int discount;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	

	public void setStartDate(String startDate) {
		if(startDate!="") {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			this.startDate = LocalDate.parse(startDate, formatter).atStartOfDay();
		}
	}

	public void setEndDate(String endDate) {
		if(endDate!="") {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			this.endDate = LocalDate.parse(endDate, formatter).atStartOfDay().plusDays(1);
		}
	}



	//저장할때 편리하게 사용가능 dto->Entity
	public SaleEntity toEntity() {
		
		return SaleEntity.builder()
				.discount(discount)
				.startDate(startDate)
				.endDate(endDate)
				.build();
	}

	
	
}
