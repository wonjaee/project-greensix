package com.coding.cho.notices.notice;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeController {
	
	private final NoticeService service;
	
	@GetMapping("/notice1")
	public String notice(Model model) {
		service.findAll(model);
		return "notice/notice/notice";		
	}
		
	@GetMapping("/notice")
	public String noticeList(Model model,
							@PageableDefault(page=0, size=10, sort="no", direction = Sort.Direction.ASC) Pageable pageable
							) {
		Page<NoticeEntity> list=service.noticeList(pageable);
		List<NoticeEntity> allList=service.noticeAllList();
		
		int nowPage = list.getPageable().getPageNumber()+1;
		int startPage = Math.max(nowPage-4, 1);
		int endPage = Math.min(nowPage+5, Math.max(1, list.getTotalPages()));
		
		model.addAttribute("allList", allList);
		model.addAttribute("list", list.getContent());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		
		
		return "notice/notice/notice";
	}
	
	
	
	
	@GetMapping("/notice-write")
	public String noticeWrite(){
		return "notice/notice/notice-write";
	}
	
	@PostMapping("/notice-write")
	public String noticeWrite2(NoticeDTO dto) {
		service.saveNotice(dto);		
		return "redirect:/notice";
	}
	@GetMapping("/notice/{no}")
	public String noticeDetail(@PathVariable("no") long no, Model model) {
		service.detail(no, model);
		return "/notice/notice/noticeDetail";
	}
	
	@DeleteMapping("/notice/{no}")
	public String delete(@PathVariable long no) {
		service.delete(no);
		return "redirect:/notice";
	}
	
	@PutMapping("/notice/{no}")
	public String updateNotice(@PathVariable long no,NoticeUpdateDTO dto) {
		service.updateProcess(no,dto);
		return "redirect:/notice/"+no;
	}
	@ResponseBody
	@GetMapping("/index/notice")
	public ModelAndView notice() {
		
		return service.noticeList();
		
	}
	
}
