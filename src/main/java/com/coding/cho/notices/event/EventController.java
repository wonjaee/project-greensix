package com.coding.cho.notices.event;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

import com.coding.cho.event.EventEntity;
import com.coding.cho.event.EventListDTO;
import com.coding.cho.event.EventService;
import com.coding.cho.event.EventUpdateDTO;
import com.coding.cho.goods.dto.GoodsUpdateDTO;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EventController {
	
	private final EventService es;
	
	
	/*
	 * @GetMapping("/event") public String event(Model model) {
	 * model.addAttribute("list", es.list());
	 * System.out.println("es.list"+es.list()); return "notice/event/event"; }
	 */
	 
	
	 
	 
	
	@Transactional
	  @GetMapping("/event") 
	  public String event(Model model,
	  
	  @PageableDefault(page=0, size=4, sort="no", direction = Sort.Direction.DESC)
	  Pageable pageable) { 
		  Page<EventEntity> list= null; 
		  List<EventEntity>
	  allList=es.eventAllList(); 
		  list = es.eventList(pageable);
		  List<EventListDTO> eld= list.getContent().stream().map(em->new EventListDTO(em)).collect(Collectors.toList());
		  System.out.println("eld="+eld);
	  
	  int nowPage = list.getPageable().getPageNumber()+1;
	  int startPage = Math.max(nowPage-4, 1);
	  int endPage = Math.min(nowPage +5, Math.max(1,list.getTotalPages()));
	  System.out.println(list.getContent().size());
	  
	  model.addAttribute("allList",allList);
	  model.addAttribute("list",eld);
	  model.addAttribute("nowPage",nowPage);
	  model.addAttribute("startPage", startPage);
	  model.addAttribute("endPage",endPage);
	  
	  
	  return "notice/event/event"; 
	  }
	 
	
	@ResponseBody
	@GetMapping("/index/event")
	public ModelAndView notice() {
		
		return es.eventList();
		
	}
	@GetMapping("/event/{no}")
	public String noticeDetail(@PathVariable("no") long no, Model model) {
		es.detail(no, model);
		return "/notice/event/eventDetail";
	}
	//관리자 페이지에서 리스트
	@GetMapping("/admin/event")
	public String adminEvent(Model model) {
		model.addAttribute("list",es.list());
		return "admin/event/adminEventList";
	}
	//관리자에서 리스트 수정 페이지
	@GetMapping("/admin/event/{no}")
	public String eventUpdate(@PathVariable long no, Model model) {
		 es.detailProcess(no, model); 
		return "admin/event/details";
	}
	//사악제
	@DeleteMapping("/admin/event/{no}")
	public String delete(@PathVariable long no) {
		es.deleteProcess(no);
		return "redirect:/admin/event";
	}
	//이벤트 업데이트~~
	@PutMapping("/admin/event/{no}")
	public String update(@PathVariable long no,EventUpdateDTO dto) {
		
		es.updateProcess(no,dto); 
		return "redirect:/admin/event";
	}
	 
	 
}
