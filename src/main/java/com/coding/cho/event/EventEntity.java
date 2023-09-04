package com.coding.cho.event;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.coding.cho.category.CategoryEntity;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.goods.GoodsImageEntity;
import com.coding.cho.goods.dto.GoodsUpdateDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "event")
@Entity
public class EventEntity {
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String content;
	
	@UpdateTimestamp
	private LocalDate createdDate;
	
	
	@OneToMany(mappedBy = "event")
	private List<EventImageEntity> gie;


	public EventEntity update(EventUpdateDTO dto) {
		this.name=dto.getName();
		this.content=dto.getContent();
		
		return this;
	}
}
