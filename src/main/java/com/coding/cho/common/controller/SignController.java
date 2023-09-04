package com.coding.cho.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding.cho.common.domain.dto.member.MemberSaveDTO;
import com.coding.cho.common.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SignController {
	
	private final MemberService service;
	
	//로그인 페이지
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("signin", "signin");
		return "sign/signup";
	}
	
	//회원가입 페이지
	@GetMapping("/signup")
	public String signup() {
		return "sign/signup";
	}
	
	//회원가입처리
	@PostMapping("/signup")
	public String signup(MemberSaveDTO dto) {
		service.saveProcess(dto);
		System.out.println(">>>>>>>"+dto);
		return "redirect:/signin";//회원가입이 성공하면 로그인페이지로 이동
	}
	
	@ResponseBody
	@GetMapping("/common/email-check")
	public boolean emailCheck(String email) {
		
		return service.emailCheckProcess(email);
	}

}
