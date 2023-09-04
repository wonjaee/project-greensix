package com.coding.cho.category;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CategoryController {
	
	private  final CategoryService service;//Constructor DI
	
	//카테고리 등록페이지 이동
	@GetMapping("/admin/category/new")
	public String write() {
		return "admin/category/write";
	}
	
	//카테고리 새로등록
	@PostMapping("/admin/category")
	public String save(String[] name) {
		service.saveProcess(name);
		return "redirect:/admin/category/new";
	}
	
	//메뉴에서 카테고리 비동기요청 
	@GetMapping("/category/{no}")
	public String categoryList(@PathVariable long no,Model model) {
		service.listProcess(no,model);
		return "admin/category/list";
	}
	//상품등록페이지에서 카테고리 비동기요청
	@GetMapping("/product/category/{no}")
	public String pdCategory(@PathVariable long no,Model model) {
		service.listProcess(no,model);
		return "admin/category/product-category";
	}
	
	//카테고리 조회
	@GetMapping("/admin/category/admin-categorylist")
	public String categorylist2(Model model) {
		service.listCategory(model);
		return "admin/category/admin-categorylist" ;
	}
	
	
	//카테고리 list 페이지에서 삭제 category.js 참고
	@ResponseBody
	@DeleteMapping("/admin/category/{no}")
	public String delete(@PathVariable long no) {
		service.deleteCategory(no);
		return "/admin/category/admin-categorylist";
	}
	
	//카테고리 수정 category.js 참고
	@ResponseBody
	@PutMapping("/admin/category/{no}")
	public String update(@PathVariable long no, @RequestParam("name") String name) {
		service.updateByNoAndName(no,name);
		System.out.println(no+name);
		return "/admin/category/admin-categorylist";
	}
	
}
