package com.coding.cho.admin;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.coding.cho.event.EventSaveDTO;
import com.coding.cho.event.EventService;
import com.coding.cho.goods.GoodsService;
import com.coding.cho.goods.dto.GoodsSaveDTO;
import com.coding.cho.goods.dto.SaleSaveDTO;
import com.coding.cho.store.FranchiseeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {

	private final FranchiseeService fs;
	private final GoodsService service;
	private final EventService es;
	
	//admin index 페이지로 이동
	@GetMapping("/admin")
	public String admin() {
		return"admin/index";
	}
	
	//상품조회
	@ResponseBody
	@GetMapping("/admin/goods")
	public ModelAndView list() {
		ModelAndView mv=new ModelAndView("admin/goods/list");
		mv.addObject("list", service.list());
		return mv;
	}
	
	
	@ResponseBody
	@PostMapping("/admin/goods")
	public void save(GoodsSaveDTO dto, SaleSaveDTO saledto) {
		System.out.println(">>>>>>>>>");
		System.out.println(saledto);
		service.save(dto,saledto);
	}
	
	//파일업로드
	@ResponseBody
	@PostMapping("/admin/goods/temp-img")
	public Map<String,String> tempUpload(MultipartFile temp){
		return service.tempUpload(temp);
	}
	
	//order 페이지
	@GetMapping("/franchisee/order")
	public String order(Model model,Authentication authentication) {
		String id= authentication.getName();
		fs.waiting(id,model);
		
		return "franchisee/order";
	}
	/*
	@ResponseBody
	@GetMapping("/franchisee/orderwait")
	public ModelAndView orderWait(Authentication authentication) {
		String id= authentication.getName();
		ModelAndView mv= new ModelAndView();
		
		return new ModelAndView("franchisee/wait").addObject("order", fs.waiting(id));
	}
	*/
	@ResponseBody
	@GetMapping("/franchisee/orderwait")
	public ModelAndView orderWait(Authentication authentication) {
		String id= authentication.getName();
		ModelAndView mv= new ModelAndView("franchisee/wait");
		mv.addObject("order", fs.waiting(id));
		return mv; 
	}
	
	@ResponseBody
	@GetMapping("/franchisee/processing")
	public ModelAndView processing() {
		
		return new ModelAndView("franchisee/processing");
	}
	@ResponseBody
	@GetMapping("/franchisee/cancel")
	public ModelAndView cancel() {
		return new ModelAndView("franchisee/cancel");
	}
	@ResponseBody
	@GetMapping("/franchisee/end")
	public ModelAndView end() {
		return new ModelAndView("franchisee/end");
	}
	
	//인덱스페이지에 상품목록 뿌리기
	//비동기-hot-item
	@GetMapping("/index/goods")
	public String goods(Model model) {
		service.hotItemList(model);
		return "index/goods/goods";
	}
	
	//비동기-할인상품
	@GetMapping("/index/sale")
	public String sale(Model model) {
		service.saleList(model);
		return "index/goods/goods";
	}
	
	//관리자 페이지에서  이벤트 등록 메뉴 누르면 등록 페이지로 이동 
	@GetMapping("/admin/event/new")
	public String write() {
		return "admin/event/write";
	}
	//이벤트 등록 페이지에서 파일업로드 추가 하면 s3 temp경로에 파일 넣을 거임
	@ResponseBody
	@PostMapping("/admin/event/temp-img")
	public Map<String,String> tempUploadEvent(MultipartFile temp){
		return es.tempUpload(temp);
	}
	//이벤트 등록 페이지에서 내용 작성하고 등록 버튼 눌렀을 시 저장 과정(비동기) 
	@ResponseBody
	@PostMapping("/admin/event")
	public void saveEvent(EventSaveDTO dto) {
		es.save(dto);
	}
	
	
}
