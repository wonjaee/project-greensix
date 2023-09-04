package com.coding.cho.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MenuController {
	
	private final MenuService ms;
	
	@GetMapping("/menu")
	public String menu() {
		return "goods/goods";
	}
	
	@GetMapping("/menu/{categoryNo}")
	public String menuList(@PathVariable long categoryNo, Model model) {
		ms.goodsList(categoryNo,model);
		
		
		return "goods/goods";
	}
	
}
