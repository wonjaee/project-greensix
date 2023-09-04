package com.coding.cho.faq;

import java.util.List;

public interface FaqCommentService {

	void commentSave(String name, long no, String commentContent);

	List<FaqCommentEntity> findAllByBoardNo(long bno);

	void updateProcess(long no, FaqCommentUpdateDTO dto);

	boolean isOwner(String name, long no);

	void commentDelete(long no);

}
