package com.coding.cho.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coding.cho.common.service.VisaulService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class VisualController {

	private final VisaulService service;
	
	/* 비동기요청 */ /* list.html리턴 */
	
	@GetMapping("/index/visual")
	public String visaulList(Model model) {
		service.visaulListProcess(model);
		return "index/visual/list";
	}
}
