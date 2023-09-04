package com.coding.cho.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.coding.cho.common.domain.entity.FaqBoardEntity;

public interface CscenterService {


	void faqListProcess(int divNo, int page, Model model);

	void faqBoardListProcess(int page, Model model);

	Page<FaqBoardEntity> boardList(Pageable pageable);

	Page<FaqBoardEntity> boardSearchList(String searchKeyword, Pageable pageable);

}
