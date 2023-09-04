package com.coding.cho.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
@Entity
public class CategoryEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_no", nullable =true)//fk
	private CategoryEntity parent;//상위카테고리
	
	@OneToMany(mappedBy = "parent",  cascade = CascadeType.REMOVE)
	private List<CategoryEntity> children;//하위카테고리목록
	
	//update를 위한 편의메서드
	public CategoryEntity updateName(String name) {
		this.name=name;
		return this;
	}

}
