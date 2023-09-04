package com.coding.cho.goods;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coding.cho.goods.dto.GoodsDetailDTO;
import com.coding.cho.goods.dto.GoodsUpdateDTO;
import com.coding.cho.goods.dto.SaleSaveDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class GoodsController {
	
	private final GoodsService service;
	
	
	
	@GetMapping("/admin/goods/new")
	public String write() {
		return "admin/goods/write";
	}
	
	@ResponseBody
	@PostMapping("/common/goods/uploadSummernoteImg")
	public Map<String, String> uploadSummernoteImg(MultipartFile file) {
		
		return service.uploadSummernoteImgProcess(file);
	}

	//상품 상세 수정
	@GetMapping("/admin/goods/{no}")
	public String goodsUpdate(@PathVariable long no, Model model) {
		service.detailProcess(no, model); 
		return "admin/goods/details";
	}
	
	@PutMapping("/admin/goods/{no}")
	public String update(@PathVariable long no,GoodsUpdateDTO dto,SaleSaveDTO savedto) {
		service.updateProcess(no,dto,savedto);
		return "redirect:/admin/goods";
	}
	@DeleteMapping("/admin/goods/{no}")
	public String delete(@PathVariable long no) {
		service.deleteProcess(no);
		return "redirect:/admin/goods";
	}
}