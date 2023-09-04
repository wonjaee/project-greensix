package com.coding.cho.chatbot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coding.cho.common.domain.entity.FaqBoardEntity;
import com.coding.cho.common.domain.entity.FaqEntity;
import com.coding.cho.common.service.FaqBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {

	private final FaqBoardService fs;
	
	
	@GetMapping("/index/chatbot/{search}")
	public String search(Model model, 
			@PageableDefault(page=0, size=4, sort="no", direction = Sort.Direction.DESC) Pageable pageable,//페이징
			@PathVariable String search){
		System.out.println("adfasdfasdfasdf" +search);
		Page<FaqEntity> searchList = null;

		
		if(search == null) {
			searchList = fs.boardList(pageable);
		}else {
			searchList = fs.boardSearchList(search, pageable);
		}
		
		int nowPage = searchList.getPageable().getPageNumber()+1;
		int startPage = Math.max(nowPage-4, 1);
		int endPage = Math.min(nowPage +5, Math.max(1, searchList.getTotalPages()));
		System.out.println(searchList.getContent().size());
		
		model.addAttribute("list", searchList.getContent());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "chatbot/list";
	}
	
	
	
	
}
