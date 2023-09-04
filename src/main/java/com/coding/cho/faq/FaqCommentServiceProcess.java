package com.coding.cho.faq;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.cho.common.domain.entity.FaqBoardEntity;
import com.coding.cho.common.domain.entity.FaqBoardEntityRepository;
import com.coding.cho.common.domain.entity.MemberEntityRepository;

@Service
public class FaqCommentServiceProcess implements FaqCommentService{

	@Autowired
	private FaqCommentEntityRepository repo;
	@Autowired
	private MemberEntityRepository uRepo;
	@Autowired
	private FaqBoardEntityRepository bRepo;
	
	//댓글 저장
	@Override
	public void commentSave(String name, long no, String commentContent) {
		repo.save(FaqCommentEntity.builder()
				.commentContent(commentContent)
				.creator(uRepo.findByEmail(name).orElseThrow())
				.board(FaqBoardEntity.builder().no(no).build())
				.build());
	}

	//댓글 리스트
	@Override
	public List<FaqCommentEntity> findAllByBoardNo(long bno) {
		return repo.findAllByBoard_noOrderByNoDesc(bno);
	}
	//댓글 수정
	@Override
	@Transactional
	public void updateProcess(long no, FaqCommentUpdateDTO dto) {
		dto.updatedDate=LocalDateTime.now();
		repo.findById(no).map(e->e.updateContent(dto));
	}
	//작성자, 로그인 계정 일치 여부 확인
	@Override
	public boolean isOwner(String name, long no) {
		FaqCommentEntity faqCommentEntity = repo.findById(no).get();
		if (faqCommentEntity != null && faqCommentEntity.getCreator().getEmail().equals(name)) {
			return true;
		}return false;
	}

	@Override
	public void commentDelete(long no) {
		repo.deleteById(no);
		
	}
	
	

}
