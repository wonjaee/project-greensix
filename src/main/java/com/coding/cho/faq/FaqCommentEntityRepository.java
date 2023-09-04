package com.coding.cho.faq;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqCommentEntityRepository extends JpaRepository<FaqCommentEntity, Long>{


	List<FaqCommentEntity> findAllByBoard_noOrderByNoDesc(long bno);

	void deleteAllByBoard_no(long no);

}
