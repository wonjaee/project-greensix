package com.coding.cho.notices.notice;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceProcess implements NoticeService{
	
	private final NoticeRepository nr;
	
	@Override
	public void saveNotice(NoticeDTO dto) {
		nr.save(NoticeEntity.builder()
				.title(dto.getTitle())
				.content(dto.getContent())				
				.build());		
		
	}

	

	@Override
	public void findAll(Model model) {
				
		List<NoticeListDTO> result = nr.findAll().stream()
									.map(noticeEntity -> new NoticeListDTO(noticeEntity)).collect(Collectors.toList());
		model.addAttribute("list", result);
		
	}

	@Override
	public void detail(long no, Model model) {
		
		model.addAttribute("detail", nr.findById(no).orElseThrow());
		
	}

	@Override
	public void delete(long no) {
		nr.deleteById(no);
		
	}


	@Transactional
	@Override
	public void updateProcess(long no, NoticeUpdateDTO dto) {
		nr.findById(no).map(e->e.update(dto));
		
	}

	@Override
	public Page<NoticeEntity> noticeList(Pageable pageable) {
		
		return nr.findAll(pageable);
	}


	@Override
	public List<NoticeEntity> noticeAllList() {
		
		return nr.findAll();
	}



	@Override
	public Object findAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ModelAndView noticeList() {
		ModelAndView mv = new ModelAndView("index/notice/notice");
		mv.addObject("notice",nr.findAll());
		return mv;
	}






	


	



	

	

}
