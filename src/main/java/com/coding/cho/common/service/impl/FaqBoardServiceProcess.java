package com.coding.cho.common.service.impl;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.coding.cho.common.domain.dto.FaqBoardSaveDTO;
import com.coding.cho.common.domain.dto.cscenter.FaqBoardUpdateDTO;
import com.coding.cho.common.domain.entity.FaqBoardEntity;
import com.coding.cho.common.domain.entity.FaqBoardEntityRepository;
import com.coding.cho.common.domain.entity.FaqEntity;
import com.coding.cho.common.domain.entity.FaqEntityRepository;
import com.coding.cho.common.domain.entity.MemberEntityRepository;
import com.coding.cho.common.service.FaqBoardService;
import com.coding.cho.faq.FaqCommentEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqBoardServiceProcess implements FaqBoardService{
	
	private final FaqBoardEntityRepository repo;
	
	private final MemberEntityRepository mRepo;
	
	private final FaqCommentEntityRepository fCrepo;
	
	private final FaqEntityRepository fer;
	

	@Override
	public void save(String userId, FaqBoardSaveDTO dto) {
		repo.save(dto.toEntity(mRepo.findByEmail(userId).get()));
	}

	@Override
	public void faqBoardDetail(long no, Model model) {
		model.addAttribute("board", repo.findById(no).get());
	}

	@Override
	public void faqBoardModify(long no, Model model) {
		model.addAttribute("board", repo.findById(no).get());
	}

	@Override
	@Transactional
	public void faqBoardDelete(long no) {
		fCrepo.deleteAllByBoard_no(no);
		repo.deleteById(no);
	}

	@Override
	public boolean isOwner(String name, long no) {
		FaqBoardEntity faqBoardEntity = repo.findById(no).get();
		if( faqBoardEntity != null && faqBoardEntity.getCreator().getEmail().equals(name)) {
			return true;
		} return false;
	}

	@Override
	@Transactional
	public void faqBoardupdate(FaqBoardUpdateDTO dto, long no) {
		repo.findById(no).map(e->e.updateTitleOrContent(dto));
		
	}


	@Override
	public Page<FaqEntity> boardList(Pageable pageable) {
		return fer.findAll(pageable);
	}

	@Override
	public Page<FaqEntity> boardSearchList(String search, Pageable pageable) {
		return fer.findByQuestionContainingOrAnswerContaining(search, search, pageable);
	}

	@Override
	public ModelAndView chatbotSearch() {
		ModelAndView mv = new ModelAndView("index/event/event");
		mv.addObject("chatbot", fer.findAll());
		return mv;
	}

	

}
