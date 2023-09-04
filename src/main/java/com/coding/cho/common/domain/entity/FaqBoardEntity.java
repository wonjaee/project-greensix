package com.coding.cho.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coding.cho.common.domain.dto.cscenter.FaqBoardUpdateDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="faq_board")
@Entity
public class FaqBoardEntity extends BaseDateEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String title;
	
	
	@Column(nullable = false)
	private String content;
	
	@JoinColumn(name = "uno", nullable = false)
	@ManyToOne
	private MemberEntity creator;
	
	public FaqBoardEntity updateTitleOrContent(FaqBoardUpdateDTO dto) {
		if(dto.getTitle()!=null)title=dto.getTitle();
		if(dto.getContent()!=null)content=dto.getContent();
		return this;
	}

}
