package com.coding.cho.common.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.coding.cho.common.domain.dto.FaqBoardSaveDTO;
import com.coding.cho.common.domain.dto.cscenter.FaqBoardUpdateDTO;
import com.coding.cho.common.domain.entity.FaqEntity;

public interface FaqBoardService {

	void save(String userId, FaqBoardSaveDTO dto);


	void faqBoardDetail(long no, Model model);


	void faqBoardModify(long no, Model model);


	void faqBoardDelete(long no);


	boolean isOwner(String name, long no);


	void faqBoardupdate(FaqBoardUpdateDTO dto, long no);



	Page<FaqEntity> boardList(Pageable pageable);


	Page<FaqEntity> boardSearchList(String search, Pageable pageable);


	ModelAndView chatbotSearch();


	//void searchFaq(String search, Model model);


}
