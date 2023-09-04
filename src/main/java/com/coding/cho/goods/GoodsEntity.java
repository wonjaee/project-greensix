package com.coding.cho.goods;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.coding.cho.category.CategoryEntity;
import com.coding.cho.goods.dto.GoodsDetailDTO;
import com.coding.cho.goods.dto.GoodsUpdateDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "goods")
@Entity
public class GoodsEntity {

	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int price;
	
	@Column(nullable = false)
	private String content;	
	
	@Column(nullable = false)
	private boolean hotItem;
	
	@OneToMany(mappedBy = "goods" ,fetch = FetchType.EAGER)
	private List<GoodsImageEntity> gie;
	
	@ManyToOne
	private CategoryEntity category;
	
	private boolean onSale;
	
	@OneToOne(mappedBy = "goods")
	private SaleEntity sale;
	
	public GoodsEntity update(GoodsUpdateDTO dto, CategoryEntity category2) {
		this.name=dto.getName();
		this.price=dto.getPrice();
		this.content=dto.getContent();
		this.hotItem=dto.isHotItem();
		this.category=category2;
		this.onSale=dto.isOnSale();
		return this;
	}
}

