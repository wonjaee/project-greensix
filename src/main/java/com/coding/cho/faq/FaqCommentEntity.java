package com.coding.cho.faq;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.coding.cho.common.domain.entity.FaqBoardEntity;
import com.coding.cho.common.domain.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="faq_comment")
@Entity
public class FaqCommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable= false)
	private String commentContent;
		
	@CreationTimestamp
	@Column(columnDefinition = "timestamp(6) null")
	private LocalDateTime createdDate;
	
	//작성자는 UserEntity와 관계설정으로
	@JoinColumn(name = "uno", nullable = true)
	@ManyToOne
	private MemberEntity creator; //creator_no
	
	//어떤 게시글의 댓글인지
	@JoinColumn(name = "bno", nullable = true)
	@ManyToOne
	private FaqBoardEntity board; //board_no

	
	public FaqCommentEntity updateContent(FaqCommentUpdateDTO dto) {
		if(dto.getContent()!=null)commentContent=dto.getContent();
		if(dto.getUpdatedDate()!=null)createdDate=dto.getUpdatedDate();
		return this;
	}
}
