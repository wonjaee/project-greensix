package com.coding.cho.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "faq")
@Getter
@Entity
public class FaqEntity {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FaqDivision division;
	@Column(nullable = false)
	private String question;
	@Column(nullable = false)
	private String answer;
}
